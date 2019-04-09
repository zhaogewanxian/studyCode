package com.wanxian.jdbc.domain;

import com.wanxian.jdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service //@Component标记的注解 派生性(注解没有继承关系)
@EnableTransactionManagement //激活
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate; //xxxxTemplate必定实现xxxxOperations :RestTemplate->RestOperations
    @Autowired
    private PlatformTransactionManager platformTransactionManager;


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

    /**
     * @param user
     * @return
     * @see AbstractPlatformTransactionManager
     * @see PlatformTransactionManager
     */
    @Override
    public boolean save2(User user) {
        boolean result = false;
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            result = save(user);
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
        }
        return result;
    }
}
