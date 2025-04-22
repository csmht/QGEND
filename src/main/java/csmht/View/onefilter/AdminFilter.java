package csmht.View.onefilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;



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

            Object obj = sei.getAttribute("admin");

            if(sei.getAttribute("id")!=null){
               if(obj.toString().equals("1")){
                   pd = "true";
               }else {
                   pd = "pass";
               }
            }else{pd="pass";}
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(pd.equals("true")){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
//            servletResponse.getWriter().write(pd);
//            request.getRequestDispatcher("/UserLogin/UserLogin.html").forward(servletRequest, servletResponse);
            servletResponse.getWriter().write(403);
        }
    }

    @Override
    public void destroy() {

    }
}
