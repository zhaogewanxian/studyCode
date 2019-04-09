package com.wanxian.version2.configuration;



import com.wanxian.version2.mapper.MapperProxy;
import com.wanxian.version2.session.SqlSession;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作框架入口
 */
public class Configuration {


    public <T> T getMapper(Class clazz, SqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, new MapperProxy(sqlSession));
    }

    /**
     * 模拟解析xml
     */
    static class TestMapperXml {
        public static final String namespace = "com.wanxian.mapper.TestMapper";
        public static final Map<String, String> sqlMap = new HashMap<>();

        static {
            sqlMap.put("selectByPrimaryKey", "select * from test where id = %d");
        }

    }
}
