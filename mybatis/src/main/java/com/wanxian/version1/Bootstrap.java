package com.wanxian.version1;

import com.wanxian.version1.Configuration;
import com.wanxian.version1.SimpleExecutor;
import com.wanxian.version1.SqlSession;
import com.wanxian.mapper.TestMapper;

import java.util.stream.Stream;

/**
 *
 */
public class Bootstrap {

    public static void main(String[] args) {
        SqlSession sqlSession = new SqlSession(new SimpleExecutor(), new Configuration());
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        Stream.of(testMapper.selectByPrimaryKey(1)).forEachOrdered(System.out::println);
    }

}
