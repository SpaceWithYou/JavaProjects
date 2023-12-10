package jetty;
import database.DbWorker;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

public class CrudServlet extends HttpServlet {

    //http://localhost:8080/query/?params=
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("SELECT:");
        int column = 1;
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String params = req.getParameter("params");
        String columnS = req.getParameter("column");
        String statement = "SELECT * from clients";
        if(params != null && !params.isEmpty()) {
            statement = "SELECT * from clients WHERE " + params;
        }
        if(columnS != null && isInteger(columnS)) {
            column = Integer.parseInt(columnS);
        }
        ResultSet set = worker.doSelectStatement(statement, worker.getConnection());
        try {
            if(set == null) {
                writer.println("No results");
                return;
            }
            while (set.next()) {
                writer.println(set.getString(column));
                System.out.println(set.getString(column));
            }
        } catch (Exception e) {
            writer.println("Error");
            writer.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("INSERT:");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String params = req.getParameter("params");
        if(params != null && !params.isEmpty()) {
            String statement = "INSERT INTO clients " + params;
            int res = worker.doNonSelectStatement(statement, worker.getConnection());
            if(res == -1) {
                writer.println("Error");
                System.out.println("Error");
                return;
            }
            writer.println("Total rows: " + res);
            System.out.println("Total rows: " + res);
        } else {
            writer.println("Error");
            System.out.println("Error");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("UPDATE:");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String params = req.getParameter("params");
        if(params != null && !params.isEmpty()) {
            String statement = "UPDATE clients \n SET " + params;
            int res = worker.doNonSelectStatement(statement, worker.getConnection());
            if(res == -1) {
                writer.println("Error");
                System.out.println("Error");
                return;
            }
            writer.println("Total rows: " + res);
            System.out.println("Total rows: " + res);
        } else {
            writer.println("Error");
            System.out.println("Error");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("DELETE:");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String params = req.getParameter("params");
        if(params != null && !params.isEmpty()) {
            String statement = "DELETE FROM clients \n WHERE" + params;
            int res = worker.doNonSelectStatement(statement, worker.getConnection());
            if(res == -1) {
                writer.println("Error");
                System.out.println("Error");
                return;
            }
            writer.println("Total rows: " + res);
            System.out.println("Total rows: " + res);
        } else {
            writer.println("Error");
            System.out.println("Error");
        }
    }
}
