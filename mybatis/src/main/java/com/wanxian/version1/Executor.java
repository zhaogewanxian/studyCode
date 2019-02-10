package com.wanxian.version1;

public interface Executor {
    <T> T query(String statement, String parameter);
}
