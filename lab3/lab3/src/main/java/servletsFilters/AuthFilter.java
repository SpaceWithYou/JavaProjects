package servletsFilters;
import services.ClientService;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/AuthPage.jsp"}, asyncSupported = true)
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = null;
        String sessionID = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie: cookies) {
                if(cookie.getName().equals("TomcatSession")) {
                    sessionID = cookie.getValue();
                    session = request.getSession();
                    session.setAttribute("TomcatSession", sessionID);
                    break;
                }
            }
        }
        if(session != null) {
            String[] strings = sessionID.split("/");
            String name = strings[0];
            String id = strings[1];
            ClientService service = new ClientService();
            if(service.search(id) != null) {
                if(service.search(id).getName().equals(name)) {
                    String page = "/ClientPage.jsp";
                    ServletContext context = request.getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher(page);
                    dispatcher.forward(request, servletResponse);
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //Filter.super.destroy();
    }
}
