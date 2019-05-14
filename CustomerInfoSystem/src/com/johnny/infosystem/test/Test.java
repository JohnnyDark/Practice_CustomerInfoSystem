package com.johnny.infosystem.test;

import com.johnny.infosystem.dao.UserDao;
import com.johnny.infosystem.dao.UserDaoImpl;
import com.johnny.infosystem.domain.User;

import java.util.List;

public class Test {
    static UserDao dao=new UserDaoImpl();

    public static void main(String[] args) {
//        testListUsers();
        testFindUser();
    }

    public static void testListUsers(){
        List<User> allUsers = dao.findAllUsers();
        System.out.println(allUsers);
    }

    public static void testFindUser(){
        User zhangsan = dao.findUser("zhangsan", "12");
        System.out.println(zhangsan);
    }

}
