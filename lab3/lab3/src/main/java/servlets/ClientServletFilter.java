package servlets;
import services.ClientService;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

public class ClientServletFilter implements Filter {
    private ClientService service;

    @Override
    public void init(FilterConfig filterConfig) {
        service = new ClientService();
        try {
            Filter.super.init(filterConfig);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
