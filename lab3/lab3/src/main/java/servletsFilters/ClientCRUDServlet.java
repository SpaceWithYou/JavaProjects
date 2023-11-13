package servletsFilters;
import clients.Client;
import services.ClientService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
    /**Create Client by name & email**/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        PrintWriter writer = resp.getWriter();
        ClientService service = new ClientService();
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService service = new ClientService();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        if(service.getCache().search(id) != null) {
            if(service.search(id).getName().equals(name)) {
                Cookie cookie = new Cookie("TomcatSession", "name:" + name + "/id:"+ id);
                resp.addCookie(cookie);
                String page = "/ClientPage.jsp";
                ServletContext context = req.getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher(page);
                dispatcher.forward(req, resp);
                return;
            }
        }
        resp.getWriter().println("<h2>Invalid name or id</h2>");
    }
    /**Update Client**/
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService service = new ClientService();
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
