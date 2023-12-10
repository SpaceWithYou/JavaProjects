package jetty;
import database.DbWorker;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {
    private static final int port = 8080;
    private Server server;
    private DbWorker worker;

    public JettyServer(String path, String role) {
        try {
            this.server = new Server(port);
            Connector connector = new ServerConnector(server);
            server.addConnector(connector);
            this.worker = new DbWorker(path, role);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/query");
            server.setHandler(context);
            context.addServlet(new ServletHolder(new CrudServlet(worker)), "/*");
            server.start();
            //server.join();
            System.out.println("Starting server");
        } catch (Exception e) {
            e.printStackTrace();
            stopServer();
        }
    }

    public void stopServer() {
        try {
            this.server.stop();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
