package com.wanxian.rmi.rpc;

public class ClientDemo {
    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy();
        TestService testService = rpcClientProxy.clientProxy(TestService.class, "localhost", 8888);
        System.out.println(testService.sayHello("wanxian"));
    }
}
