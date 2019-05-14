package com.johnny.infosystem.web;

import com.johnny.infosystem.domain.PageBean;
import com.johnny.infosystem.domain.User;
import com.johnny.infosystem.service.UserService;
import com.johnny.infosystem.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取参数
        String currentPage = request.getParameter("currentPage"); //客户端传递的当前页码
        String rows=request.getParameter("rows"); //客户端传递的每页显示的条数

        if (currentPage==null || "".equals(currentPage)){
            currentPage="1";
        }
        if (rows==null || "".equals(rows)){
            rows="5";
        }

        //获取所有查询条件的参数值（currentPage，rows，name, address, email）
        Map<String, String[]> conditions = request.getParameterMap();
        System.out.println("conditions : "+conditions);

        UserService service=new UserServiceImpl();

        //将复杂查询条件添加到业务处理方法中，并返回所期望的PageBean对象
        PageBean<User> pb=service.findUserByPage(currentPage, rows, conditions);

//        System.out.println(pb);

        //处理分页查询后的PageBean对象
        request.setAttribute("pb",pb);
        request.setAttribute("conditions",conditions);
        request.getRequestDispatcher("/list.jsp").forward(request, response);

    }
}
