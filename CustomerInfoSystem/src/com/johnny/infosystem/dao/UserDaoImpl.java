package com.johnny.infosystem.dao;

import com.johnny.infosystem.domain.User;
import com.johnny.infosystem.utils.JdbcUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 调用JdbcTemplate的方法来完成CRUD的操作
        * update():执行DML语句。增、删、改语句
        * queryForMap():查询结果将结果集封装为map集合，将列名作为key，将值作为value 将这条记录封装为一个map集合
            * 注意：这个方法查询的结果集长度只能是1
        * queryForList():查询结果将结果集封装为list集合
            * 注意：将每一条记录封装为一个Map集合，再将Map集合装载到List集合中
        * query():查询结果，将结果封装为JavaBean对象
            * query的参数：RowMapper
                * 一般我们使用BeanPropertyRowMapper实现类。可以完成数据到JavaBean的自动封装
                * new BeanPropertyRowMapper<类型>(类型.class)
        * queryForObject：查询结果，将结果封装为对象
            * 一般用于聚合函数的查询
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JdbcUtil.getDataSource());
    @Override
    public List<User> findAllUsers() {
        String sql = "select * from user";
        List<User> users=template.query(sql,new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User findUser(String username,String password) {
        try {
            String sql = "select * from user where username=? and password=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void add(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?,null,null)";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void deleteSingle(int id) {
        String sql = "delete from user where id=?";
        template.update(sql, id);
    }

    @Override
    public User findSingle(int id) {
        String sql = "select * from user where id=?";
        try {
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
            return user;
        } catch (DataAccessException e) {
            return null;
        }

    }

    @Override
    public void updateSingle(User user) {
        String sql = "update user set name=?, gender=?, age=?, address=?, qq=?, email=? where id=?";
        template.update(sql,user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> conditions) {
        //定义初始化模板
        String sql="select count(*) from user where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        /*
            实现复杂查询时，sql语句生成逻辑
         */
        //定义list集合，保存具体的查询条件的值（name，address，email）
        List<Object> values = new ArrayList<Object>();

        //通过request.getParameterMap()获取到请求参数和值的map集合
        Set<String> keySet=conditions.keySet();
        //循环遍历map，获取请求参数，即复杂查询中的key，获取请求的参数值，即复杂查询中的条件值
        for (String key : keySet) {
            //排除分页查询条件
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取插叙参数的值
            String value=conditions.get(key)[0];
            if (!"".equals(value) && value != null) {
                values.add("%"+value+"%");
                sb.append(" and "+key+" like ? ");
            }
        }//完成sql语句中需要的内容获取，组装完整的sql语句

        int totalCount=template.queryForObject(sb.toString(),Integer.class,values.toArray());
        return totalCount;
    }

    @Override
    public List<User> findByPage(int startPage, int rows, Map<String, String[]> conditions) {
        String sql="select * from user where 1=1";
        StringBuilder sb = new StringBuilder(sql);

        System.out.println(startPage+" : "+rows);

        //定义查询参数值的集合
        List<Object> values = new ArrayList<Object>();
        //遍历请求参数map
        Set<String> keySet=conditions.keySet();
        for (String key : keySet) {
            //排除分页查询条件
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取插叙参数的值
            String value=conditions.get(key)[0];
            if (!"".equals(value) && value != null) {
                values.add("%"+value+"%");
                sb.append(" and "+key+" like ? ");
            }
        }
        //添加分页查询
        sb.append(" limit ?,?");

        System.out.println(sb.toString());

        values.add(startPage);
        values.add(rows);

        System.out.println(values);

        return template.query(sb.toString(),new BeanPropertyRowMapper<User>(User.class),values.toArray());
    }

}
