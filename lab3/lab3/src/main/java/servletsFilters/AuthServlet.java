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

@WebServlet(urlPatterns = "/AuthPage.jsp", loadOnStartup = 1)
public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ClientService service = new ClientService();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        if(id != null || name != null ) {
            Client search = service.search(id);
            if(search != null) {
                if(search.getName().equals(name)) {
                    for(Cookie cookie: req.getCookies()) {
                        if(cookie.getName().equals("TomcatSession")) {
                            cookie.setValue("name:" + name + "/id:"+ id);
                        }
                    }
//                    Cookie cookie = new Cookie("TomcatSession", "name:" + name + "/id:"+ id);
//                    cookie.setMaxAge(18000);
//                    cookie.setPath("/lab3");
//                    cookie.setHttpOnly(true);
//                    resp.addCookie(cookie);
                    resp.setContentType("text/html");
                    resp.getWriter().println("<h2>Success</h2>");
                    return;
                }
            }
        }
        resp.getWriter().println("<h2>Invalid name or id</h2>");
    }
}
