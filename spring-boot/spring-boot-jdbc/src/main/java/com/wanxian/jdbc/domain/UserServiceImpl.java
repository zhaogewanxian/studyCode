package com.wanxian.jdbc.domain;

import com.wanxian.jdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service //@Component标记的注解 派生性(注解没有继承关系)
@EnableTransactionManagement //激活
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate; //xxxxTemplate必定实现xxxxOperations :RestTemplate->RestOperations

    @Transactional
    @Override
    public boolean save(User user) {
        /**
         * 裸实现
         */
        Boolean result = jdbcTemplate.execute("INSERT INTO user (username,password) values (?,?)", new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                return preparedStatement.executeUpdate() > 0;
            }
        });
        return result;
    }
}
