package com.johnny.infosystem.service;

import com.johnny.infosystem.dao.UserDao;
import com.johnny.infosystem.dao.UserDaoImpl;
import com.johnny.infosystem.domain.PageBean;
import com.johnny.infosystem.domain.User;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    UserDao dao = new UserDaoImpl();

    @Override
    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    @Override
    public User login(User loginUser) {
        User user = dao.findUser(loginUser.getUsername(), loginUser.getPassword());
        return user;
    }

    @Override
    public void addUser(User user) {
        dao.add(user);
    }

    @Override
    public void delUser(String id) {
        dao.deleteSingle(Integer.parseInt(id));
    }

    @Override
    public User findSingleUser(String id) {
        User user = dao.findSingle(Integer.parseInt(id));
        return user;
    }

    @Override
    public void updateUser(User user) {
        dao.updateSingle(user);
    }

    /**
     * 复杂分页查询
     * @param _currentPage
     * @param _rows
     * @param conditions：所有的查询条件
     * @return
     */
    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> conditions) {

        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        //test
        System.out.println("currentPage : "+currentPage);
        System.out.println("rows : "+rows);

        //dao查询符合条件的数据数量
        int totalCount = dao.findTotalCount(conditions);
        System.out.println("totalCount : "+totalCount);

        int totalPage = (totalCount % rows) == 0 ? (totalCount / rows) : (totalCount / rows + 1);
        System.out.println("totalPage : "+totalPage);

        if (currentPage<=0){
            currentPage=1;
        }

        if (currentPage>=totalPage && totalPage>0){
            currentPage=totalPage;
        }

        PageBean<User> pb = new PageBean<User>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);


        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);

        System.out.println(currentPage);

        int startPage=(currentPage-1)*rows;

        //test
        System.out.println("startPage : "+startPage);

        //查询符合条件的所有User对象，封装到List集合中
        List<User> users=dao.findByPage(startPage,rows, conditions);
        pb.setList(users);
        return pb;
    }
}
