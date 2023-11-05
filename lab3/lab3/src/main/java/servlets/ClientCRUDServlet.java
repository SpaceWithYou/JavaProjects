package servlets;
import clients.Client;
import services.ClientService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/page", loadOnStartup = 1)
public class ClientCRUDServlet extends HttpServlet {
    /**Create Client by name & email**/
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        PrintWriter writer = resp.getWriter();
        ClientService service = new ClientService();
        if(name == null || email == null) {
            writer.println("<p>Error</p>");
            writer.close();
            return;
        }
        if(service.getCache().uniq(email)) {
            Client client = new Client(name, email);
            service.create(client);
            writer.println("<h2>Created new Client with id = " + client.getId() + "</h2>");
            writer.close();
        }
        writer.println("<p>Already exists</p>");
        writer.close();
    }
    /**Get Client by id**/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService service = new ClientService();
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String id = req.getParameter("id");
        if(id == null) {
            writer.println("<p>Unknown id</p>");
            writer.close();
            return;
        }
        writer.write("<h2>" + service.getCache().search(id).toString() + "</h2>");
    }
    /**Update Client**/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService service = new ClientService();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String id = req.getParameter("id");
        String[] params = req.getParameterValues("params");
        if(id == null || params.length > 2 || params[0] == null || params[1] == null) {
            writer.println("<p>Error</p>");
            writer.close();
            return;
        }
        service.update(id, params);
    }
    /**Delete Client**/
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService service = new ClientService();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String id = req.getParameter("id");
        if(id == null || service.search(id) == null) {
            writer.println("<p>Unknown id</p>");
            writer.close();
            return;
        }
        service.delete(id);
        writer.println("<p>Deleting Client</p>");
        writer.close();
    }
}
