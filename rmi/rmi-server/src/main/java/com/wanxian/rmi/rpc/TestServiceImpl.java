package com.wanxian.rmi.rpc;

public class TestServiceImpl implements TestService{
    @Override
    public String say(String msg) {
        return "hello,"+msg;
    }
}
