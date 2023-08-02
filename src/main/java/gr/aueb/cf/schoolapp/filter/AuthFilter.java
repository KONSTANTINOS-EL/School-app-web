package gr.aueb.cf.schoolapp.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        boolean authenticated = false;

        String requestURI = req.getRequestURI();
//        if (requestURI.endsWith(".css")) {
//            filterChain.doFilter(request, response);
//        } else {

            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        HttpSession session = req.getSession(false);
                        authenticated = (session != null) && (session.getAttribute("loginName") != null);
                    }
                }
            }
//        }



        if (authenticated) {
            filterChain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
