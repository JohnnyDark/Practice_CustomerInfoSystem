package com.johnny.infosystem.dao;

import com.johnny.infosystem.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<User> findAllUsers();
    /**
     * 按账号密码查询用户对象，登录查询
     */
    User findUser(String username, String password);

    void add(User user);

    /**
     * 删除单个用户
     * @param i
     */
    void deleteSingle(int i);

    /**
     * 按id查找单个用户，修改用户信息时用
     * @param id
     * @return
     */
    User findSingle(int id);

    /**
     * 提交修改后的用户信息
     * @param user
     */
    void updateSingle(User user);

    /**
     * 查找符合所有查询条件的用户数量
     * @param conditions
     * @return
     */
    int findTotalCount(Map<String, String[]> conditions);

    /**
     * 查找符合所有条件的用户具体信息，并封装对象，保存到list集合中
     * @param startPage
     * @param rows
     * @param conditions
     * @return
     */
    List<User> findByPage(int startPage, int rows, Map<String, String[]> conditions);
}
