package com.wanxian.jdbc.controller;

import com.wanxian.jdbc.domain.User;
import com.wanxian.jdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JdbcController {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserService userService;

    /**
     * @param id
     * @return
     * @see AutoCloseable
     */
    @RequestMapping("getUser")
    public Map<String, Object> getUser(int id) {
        Map<String, Object> map = new HashMap<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            //Statement statement = connection.createStatement();//
            //Statement statement = connection.prepareStatement("SELECT id,username,password  FROM user where id=" + id);//
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,username,password  FROM user where id=?"); //预编译 防止sql注入
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                map.put("id", resultSet.getInt("id"));
                map.put("username", resultSet.getString("username"));
                map.put("password", resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close(); //java 1.7+ connection已实现自动关闭接口java.lang.AutoCloseable 无需进行关闭
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @RequestMapping("save")
    public Map<String, Object> save(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", userService.save(user));
        return map;
    }

}
