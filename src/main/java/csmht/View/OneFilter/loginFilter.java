package csmht.View.OneFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;


@WebFilter(urlPatterns = {"/User/*","/View/*"})
public class loginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        String[] urls = {".css",".js"};
        String url = request.getRequestURL().toString();
        for (String i:urls){
            if(url.contains(i)){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        HttpSession sei = request.getSession();
        String pd = "false";
        try {
            if(sei.getAttribute("id")!=null){
                    pd = "true";
            }else{pd="pass";}
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if(pd.equals("true")){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
//            servletResponse.getWriter().write(pd);
            request.getRequestDispatcher("/UserLogin/UserLogin.html").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
