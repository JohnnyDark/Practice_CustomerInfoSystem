package com.johnny.infosystem.filter;

import com.johnny.infosystem.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //设置编码表
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        //登录过滤逻辑
        HttpServletRequest request= (HttpServletRequest) req;
        String uri=request.getRequestURI();
        if (uri.contains("/login") || uri.contains("/checkCode") || uri.contains("/js/") || uri.contains("/css/") || uri.contains("/fonts/")) {
            chain.doFilter(req, resp);
        }else{
            User user= (User) request.getSession().getAttribute("user");
            if (user!=null){
                chain.doFilter(req,resp);
            }else {
                request.setAttribute("log_msg","您尚未登录，登录后可操作");
                request.getRequestDispatcher("/index.jsp").forward(req,resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
