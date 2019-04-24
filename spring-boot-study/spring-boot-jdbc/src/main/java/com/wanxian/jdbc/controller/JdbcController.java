package com.wanxian.jdbc.controller;

import com.wanxian.jdbc.domain.User;
import com.wanxian.jdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JdbcController {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    /**
     * @return
     * @see JdbcTemplate
     */
    @RequestMapping("getUsers")
    public List<Map<String, Object>> getUsers() {
        return jdbcTemplate.execute(new StatementCallback<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> doInStatement(Statement statement) throws SQLException, DataAccessException {
                List<Map<String, Object>> resultList = new ArrayList<>();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
                List<String> columnNames = new ArrayList<>();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); //结果集元数据
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) { //下标从1开始
                    columnNames.add(resultSetMetaData.getColumnName(i)); //拿到字段名称
                }
                while (resultSet.next()) {
                    Map<String, Object> map = new HashMap<>();
                    for (String columnName :
                            columnNames) {
                        Object columnValue = resultSet.getObject(columnName); //根据字段名称获取字段值
                        map.put(columnName, columnValue);
                    }
                    resultList.add(map);
                }
                return resultList;
            }
        });
    }


    @RequestMapping("save")
    public Map<String, Object> save(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", userService.save(user));
        map.put("success", userService.save2(user));
        return map;
    }

}
