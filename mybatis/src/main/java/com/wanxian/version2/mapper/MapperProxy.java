package com.wanxian.version2.mapper;


import com.wanxian.version2.configuration.Configuration;
import com.wanxian.version2.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy implements InvocationHandler {
    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass().getName().equals(Configuration.TestMapperXml.namespace)) {
            String sql = Configuration.TestMapperXml.sqlMap.get(method.getName());
            return sqlSession.selectOne(sql, String.valueOf(args[0])); //代理方法，由sqlSession执行,(此处拿到mapper中接口名称->对应的sql);
        }
        return method.invoke(this, args);
    }
}
