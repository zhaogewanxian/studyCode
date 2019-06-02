package com.wanxian.rmi.rpc;

public class ServerDemo {
    public static void main(String[] args) {
        TestService testService = new TestServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.publisher(testService, 8888);
    }
}
