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
    public void init(FilterConfig filterConfig) throws ServletException {
        //Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String params = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie: cookies) {
                if(cookie.getName().equals("TomcatSession")) {
                    params = (String) request.getAttribute("TomcatSession");
                    break;
                }
            }
        }
        if(params != null) {
            if(!params.isEmpty()) {
                String[] strings = params.split("/");
                String name = strings[0];
                String id = strings[1];
                response.getWriter().println(name);
                response.getWriter().println(id);
                ClientService service = new ClientService();
                if(service.search(id) != null) {
                    if(service.search(id).getName().equals(name)) {
                        servletResponse.getWriter().println("<h2>Logged as " + name + "</h2>");
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                }
            }
        }
        Cookie cookie = new Cookie("TomcatSession", "");
        cookie.setMaxAge(18000);
        cookie.setPath("/lab3");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
