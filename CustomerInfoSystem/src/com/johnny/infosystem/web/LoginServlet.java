package com.johnny.infosystem.web;

import com.johnny.infosystem.domain.User;
import com.johnny.infosystem.service.UserService;
import com.johnny.infosystem.service.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    UserService service=new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String verifyCode=request.getParameter("verifycode");
        String sessionVerifyCode= (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        //判断验证码
        if (!verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
            //验证码不正确
            request.setAttribute("login_msg","验证码错误");
            request.getRequestDispatcher("/index.jsp").forward(request,response);
            return;
        }
        //封装登陆信息
        User user = new User();
        Map<String, String[]> map=request.getParameterMap();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        user= service.login(user);
        if (user!=null){
            request.getSession().setAttribute("user",user);
            response.sendRedirect(request.getContextPath()+"/entrance.jsp");
        }else{
            request.setAttribute("login_msg","用户名或密码错误");
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
