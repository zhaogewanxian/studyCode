package com.wanxian.version1;

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
