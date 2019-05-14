package com.johnny.infosystem.web;

import com.johnny.infosystem.service.UserService;
import com.johnny.infosystem.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delMultipleUserServlet")
public class DelMultipleUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] ids=request.getParameterValues("ids");
        UserService service=new UserServiceImpl();
        if (ids!=null && ids.length>0){
            for (String id : ids) {
                service.delUser(id);
            }
        }
        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet");
    }
}
