package com.wanxian.rmi.rpc;

import java.io.Serializable;
import java.lang.reflect.Proxy;

public class RpcClientProxy implements Serializable {
    public <T> T clientProxy(final Class<T> interfaceCls, final String host, final int port) {
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class[]{interfaceCls}, new RemoteInvocationHandler(host, port));
    }
}
