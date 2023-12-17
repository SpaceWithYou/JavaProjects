package jetty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import database.DbWorker;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.stream.Collectors;

/**Приватный класс JettyClient, чтобы не был не доступен вне класса**/
public class CrudServlet extends HttpServlet {
    /**Private class for JSON format read/write**/
    private static class JettyClient {
        private String name;
        private String surname;
        private String lastname;
        private int birthyear;
        private long number;
        private int id;
        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getLastname() {
            return lastname;
        }

        public int getBirthyear() {
            return birthyear;
        }

        public long getNumber() {
            return number;
        }

        public int getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public void setBirthyear(int birthyear) {
            this.birthyear = birthyear;
        }

        public void setNumber(long number) {
            this.number = number;
        }

        public void setId(int id) {
            this.id = id;
        }

        private JettyClient(String name, String surname, String lastname, int birthyear, long number) {
            this.name = name;
            this.surname = surname;
            this.lastname = lastname;
            this.birthyear = birthyear;
            this.number = number;
        }

        private JettyClient(int id) { this.id = id; }
        private JettyClient() { }
    }
    private final DbWorker worker;

    CrudServlet(DbWorker worker) {
        this.worker = worker;
    }

    /**Determines whether a string s is a number or not
     * @return true if s is integer, false otherwise**/
    private boolean isInteger(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**Checks params**/
    private boolean check(String s) {
        return s != null && !s.isEmpty();
    }

    /**Get by id or get all <br>
     * Checks for flag (all = true) - outputs all users <br>
     * otherwise checks id <br>
     * SELECT**/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("SELECT:");
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String all = req.getParameter("all");
        String statement;
        if(check(all) && all.equals("true")) {
            statement = "SELECT * from clients";
        } else {
            String id = req.getParameter("id");
            if(check(id) && isInteger(id)) {
                statement = "SELECT * from clients WHERE id = " + id;
            } else {
                writer.println("Error");
                System.out.println("Error");
                return;
            }
        }
        ResultSet set = worker.doSelectStatement(statement, worker.getConnection());
        if(set == null) {
            writer.println("No matches");
            System.out.println("No matches");
            return;
        }
        try {
            //return Database row in JSON format
            ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);
            String val;
            while(set.next()) {
                JettyClient client = new JettyClient(set.getInt(1));
                client.setName(set.getString(2));
                client.setSurname(set.getString(3));
                client.setLastname(set.getString(4));
                client.setBirthyear(set.getInt(5));
                client.setNumber(set.getLong(6));
                val = mapper.writeValueAsString(client);
                System.out.println(val);
                writer.println(val);
            }
        } catch (Exception e) {
            writer.println("Error");
            writer.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**Get Client in JSON format (params = JSON client)<br>
     * using Jackson library, id указывать не надо<br>
     * if something goes wrong Database show it**/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("INSERT:");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        ObjectMapper mapper = new ObjectMapper();
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JettyClient client;
        try {
            client = mapper.readValue(reqBody, JettyClient.class);
        } catch (Exception e) {
            writer.println("Error");
            writer.println(e.getMessage());
            e.printStackTrace();
            return;
        }
        String values = String.format("('%s', '%s', '%s', %d, %d)", client.getName(), client.getSurname(), client.getLastname(), client.getBirthyear(), client.getNumber());
        String statement = "INSERT INTO clients(firstname, secondname, lastname, birthyear, number) VALUES " + values;
        int res = worker.doNonSelectStatement(statement, worker.getConnection());
        if(res == -1) {
            writer.println("Error");
            System.out.println("Error");
            return;
        }
        writer.println("Total rows: " + res);
        System.out.println("Total rows: " + res);
    }

    /**Update by id**/
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("UPDATE:");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String idString = req.getParameter("id");
        if(idString != null && isInteger(idString)) {
            int id = Integer.parseInt(idString);
            String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            ObjectMapper mapper = new ObjectMapper();
            JettyClient client;
            try {
                 client = mapper.readValue(reqBody, JettyClient.class);
            } catch (Exception e) {
                writer.println("Error");
                writer.println(e.getMessage());
                e.printStackTrace();
                return;
            }
            String values = String.format("firstname = '%s', secondname = '%s', lastname = '%s', birthyear = %d, number = %d",
                    client.getName(), client.getSurname(), client.getLastname(), client.getBirthyear(), client.getNumber());
            String statement = "UPDATE clients \n SET " + values + "\n WHERE id = " + id;
            int res = worker.doNonSelectStatement(statement, worker.getConnection());
            if(res == -1) {
                writer.println("Error");
                System.out.println("Error");
                return;
            }
            writer.println("Total rows: " + res);
            System.out.println("Total rows: " + res);
            return;
        }
        writer.println("Error");
        System.out.println("Error");
    }

    /**By id**/
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("DELETE:");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String idString = req.getParameter("id");
        if(idString != null && isInteger(idString)) {
            String statement = "DELETE FROM clients \n WHERE id = " + idString;
            int res = worker.doNonSelectStatement(statement, worker.getConnection());
            if(res == -1) {
                writer.println("Error");
                System.out.println("Error");
                return;
            }
            writer.println("Total rows: " + res);
            System.out.println("Total rows: " + res);
            return;
        }
        writer.println("Error");
        System.out.println("Error");
    }
}
