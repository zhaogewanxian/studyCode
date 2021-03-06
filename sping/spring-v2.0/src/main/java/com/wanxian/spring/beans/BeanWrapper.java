package com.wanxian.spring.beans;

import com.wanxian.spring.aop.AopConfig;
import com.wanxian.spring.aop.AopProxy;
import com.wanxian.spring.core.FactoryBean;

public class BeanWrapper extends FactoryBean {
    private AopProxy aopProxy = new AopProxy();
    private BeanPostProcessor beanPostProcessor;
    private Object wrapperInstance; //包装对象
    private Object originalInstance;//原生对象

    public BeanWrapper(Object instance) {
        this.wrapperInstance = aopProxy.getProxy(instance);
        this.originalInstance = instance;
    }

    public Object getWrappedInstance() {
        return this.wrapperInstance;
    }

    public BeanPostProcessor getBeanPostProcessor() {
        return beanPostProcessor;
    }

    public void setBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessor = beanPostProcessor;
    }

    private Object getWrapperInstance() {
        return this.wrapperInstance;
    }

    //$Proxy0
    public Class<?> getWrappedClass() {
        return this.wrapperInstance.getClass();
    }

    public Object getOriginalInstance() {
        return originalInstance;
    }

    public void setAopConfig(AopConfig aopConfig) {
        this.aopProxy.setConfig(aopConfig);
    }
}
