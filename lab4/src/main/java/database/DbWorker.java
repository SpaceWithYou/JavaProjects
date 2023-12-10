package database;
import java.io.FileReader;
import java.sql.*;

public class DbWorker {
    private final String password;
    private static final String url = "jdbc:postgresql://127.0.0.1:5432/clientsdb";
    private final String role;

    public DbWorker(String path, String role) {
        try(FileReader file = new FileReader(path)) {
            int c;
            StringBuilder builder = new StringBuilder();
            while ((c = file.read()) != -1) {
                builder.append((char) c);
            }
            this.password = builder.toString();
            this.role = role;
        } catch (Exception e)  {
            throw new RuntimeException();
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, role, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**stType - select, insert, update, delete**/
    public ResultSet doSelectStatement(String statement, Connection connection) {
        try {
            Statement st = connection.createStatement();
            return st.executeQuery(statement);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int doNonSelectStatement(String statement, Connection connection) {
        try {
            Statement st = connection.createStatement();
            return st.executeUpdate(statement);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
