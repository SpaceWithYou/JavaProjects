import jetty.JettyServer;


public class Main {
    public static void main(String[] args) {
        String path = "C:\\Users\\Артемий\\Downloads\\Key\\password.txt";
        String role = "postgres";
        JettyServer server = new JettyServer(path, role);
    }
}
