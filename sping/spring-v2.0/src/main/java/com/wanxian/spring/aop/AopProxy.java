package com.wanxian.spring.aop;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//默认使用Jdk 动态代理,实现接口使用jdk，否则使用cglib
public class AopProxy implements InvocationHandler {
    private Object target;
    private AopConfig config;


    public void setConfig(AopConfig config) {
        this.config = config;
    }

    //把原生对象传入
    public Object getProxy(Object instance) {
        this.target = instance;
        return Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //传入进来的是代理之后对象,需转换为原始
        Method m = this.target.getClass().getMethod(method.getName(),method.getParameterTypes());
        if (config.contanins(m)) {//方法调用之前
            AopConfig.Aspect aspect = config.get(method);
            //aspect.getPoints()[0].invoke(aspect.getAspect());
        }
        Object obj = method.invoke(this.target, args);
        if (config.contanins(m)) { //方法调用之后
            AopConfig.Aspect aspect = config.get(method);
           // aspect.getPoints()[1].invoke(aspect.getAspect());
        }
        return obj;
    }
}
