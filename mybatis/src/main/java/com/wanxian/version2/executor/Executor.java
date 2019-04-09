package com.wanxian.version2.executor;

public interface Executor {
    <T> T query(String statement, String parameter);
}
