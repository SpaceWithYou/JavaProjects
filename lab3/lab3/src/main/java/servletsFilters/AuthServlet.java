package servletsFilters;
import clients.Client;
import services.ClientService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/AuthPage.jsp/Auth", loadOnStartup = 1)
public class AuthServlet extends HttpServlet {
private ClientService service = new ClientService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("AuthServlet");
        String session = req.getHeader("TomcatSession");
        String[] params = session.split("/");
        String id = params[1].replace("id:", "");
        String name = params[0].replace("name:", "");
        PrintWriter writer = resp.getWriter();
        if(!id.isEmpty() || !name.isEmpty()) {
            Client search = service.search(id);
            if(search != null) {
                if(search.getName().equals(name)) {
                  Cookie cookie = new Cookie("TomcatSession", "name:" + name + "/id:"+ id);
                  cookie.setMaxAge(18000);
                  cookie.setPath("/lab3");
                  cookie.setSecure(true);
                  resp.addCookie(cookie);
                  System.out.println("AuthServlet added cookie");
                  writer.println("<h2>Success</h2>");
                  writer.close();
                  return;
                }
            }
        }
        writer.println("<h2>Invalid name or id</h2>");
        writer.close();
    }

}
