package com.johnny.infosystem.web;

import com.johnny.infosystem.service.UserService;
import com.johnny.infosystem.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delSingleUserServlet")
public class DelSingleUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("id");
        UserService service=new UserServiceImpl();
        service.delUser(id);
        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet");
    }
}
