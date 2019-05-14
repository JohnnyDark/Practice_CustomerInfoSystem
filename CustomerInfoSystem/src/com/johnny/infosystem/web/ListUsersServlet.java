package com.johnny.infosystem.web;

import com.johnny.infosystem.domain.User;
import com.johnny.infosystem.service.UserService;
import com.johnny.infosystem.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/listUsersServlet")
public class ListUsersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService service=new UserServiceImpl();
        List<User> allUsers = service.findAllUsers();
        request.setAttribute("allUsers",allUsers);
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}
