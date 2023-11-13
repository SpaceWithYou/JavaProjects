package servletsFilters;
import clients.Client;
import services.ClientService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/page", loadOnStartup = 1)
public class ClientCRUDServlet extends HttpServlet {
    private ClientService service;
    @Override
    public void init() throws ServletException {
        super.init();
        service = new ClientService();
    }

    /**Create Client by name & email**/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        PrintWriter writer = resp.getWriter();
        if(name.isEmpty() || email.isEmpty()) {
            writer.println("<p>Error</p>");
            writer.close();
            return;
        }
        if(service.getCache().uniq(email)) {
            Client client = new Client(name, email);
            service.create(client);
            writer.println("<p>Created new Client with id = " + client.getId() + "</p>");
            writer.close();
            return;
        }
        writer.println("<p>Already exists</p>");
        writer.close();
    }
    /**Get Client by id**/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        PrintWriter writer = resp.getWriter();
        if(id == null ) {
            writer.println("<p>Error</p>");
            return;
        }
        Client search = service.search(id);
        if(search == null) {
            writer.println("<p>Error</p>");
            return;
        }
        writer.println("<h2>Name: " +  search.getName() + "</h2>");
        writer.println("<h2>Email: " + search.getEmail() + "</h2>");
        writer.println("<h2>Id: " + search.getId() + "</h2>");
    }
    /**Update Client**/
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String id = req.getParameter("id");
        String[] params = req.getParameterValues("params");
        if(id.isEmpty() || params.length > 2 || params[0].isEmpty() || params[1].isEmpty()) {
            writer.println("<p>Error</p>");
            writer.close();
            return;
        }
        service.update(id, params);
    }
    /**Delete Client**/
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
