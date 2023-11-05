package servlets;
import services.ClientService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 0)
public class CacheInitServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        ClientService service = new ClientService();
        super.init(config);
    }
}
