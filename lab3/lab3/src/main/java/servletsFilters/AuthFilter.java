package servletsFilters;
import services.ClientService;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/AuthPage.jsp"})
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        //Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("AuthFilter");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String params = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie: cookies) {
                if(cookie.getName().equals("TomcatSession")) {
                    params = cookie.getValue();
                    break;
                }
            }
        }
        if(!params.isEmpty()) {
            String[] strings = params.split("/");
            String name = strings[0].replace("name:", "");
            String id = strings[1].replace("id:", "");
            response.getWriter().println(name);
            response.getWriter().println(id);
            ClientService service = new ClientService();
            if(service.search(id) != null) {
                if(service.search(id).getName().equals(name)) {
                    servletResponse.getWriter().println("<h2>Logged as " + name + "</h2>");
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        System.out.println("AuthFilterChain");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
