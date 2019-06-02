package com.wanxian.rmi.rpc;

public class TestServiceImpl implements TestService {
    @Override
    public String sayHello(String msg) {
        return "hello," + msg;
    }
}
