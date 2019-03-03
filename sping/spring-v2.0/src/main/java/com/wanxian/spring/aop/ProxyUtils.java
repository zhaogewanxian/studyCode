package com.wanxian.spring.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class ProxyUtils {

    public static Object getTargetObject(Object object) throws Exception {
        if (isAopProxy(object)) {
            return getProxyTargetObject(object);
        }
        return object;
    }

    private static Object getProxyTargetObject(Object object) throws Exception {
        Field h = object.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(object);
        Field target = aopProxy.getClass().getDeclaredField("target");
        target.setAccessible(true);
        return target.get(aopProxy);
    }

    private static boolean isAopProxy(Object object) {
        return Proxy.isProxyClass(object.getClass());

    }

}
