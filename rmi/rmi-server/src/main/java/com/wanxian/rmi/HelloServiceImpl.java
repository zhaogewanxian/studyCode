package com.wanxian.rmi;

import javax.net.ssl.HostnameVerifier;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        return "hello,"+msg;
    }
}
