package com.johnny.infosystem.service;

import com.johnny.infosystem.domain.PageBean;
import com.johnny.infosystem.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */
    List<User> findAllUsers();

    /**
     * 登录相关业务
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * 删除单个用户信息
     * @param id
     */
    void delUser(String id);

    /**
     * 更具id查找单个用户信息
     * @param id
     * @return
     */
    User findSingleUser(String id);

    /**
     * 修改用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 查找符合条件的用户新
     * @param currentPage：客户端发送的当前页码
     * @param rows：客户端发送的每页展示数据量
     * @param conditions：所有的查询条件
     * @return PageBean对象
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> conditions);
}
