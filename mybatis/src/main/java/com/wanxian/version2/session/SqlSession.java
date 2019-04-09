package com.wanxian.version2.session;


import com.wanxian.version2.configuration.Configuration;
import com.wanxian.version2.executor.Executor;

public class SqlSession {
    private Executor executor;
    private Configuration configuration;

    public SqlSession(Executor executor, Configuration configuration) {
        this.executor = executor;
        this.configuration = configuration;
    }

    public <T> T getMapper(Class clazz) {
        return configuration.getMapper(clazz,this);
    }

    /**
     * @param statement sql语句
     * @param parameter sql参数
     * @param <T>
     * @return
     */

    public <T> T selectOne(String statement, String parameter) {
        return executor.query(statement, parameter);

    }
}
