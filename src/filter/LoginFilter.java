package filter;

import bean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
        //添加登录的过滤器
        HttpServletRequest request=(HttpServletRequest) req;

        User user=(User) request.getSession().getAttribute("user");
        if (user==null){
            request.getRequestDispatcher("/jsps/user/login.jsp").forward(request,resp);
        }else {
            //放行
            chain.doFilter(request,resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
