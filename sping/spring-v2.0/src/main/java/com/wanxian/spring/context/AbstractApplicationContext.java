package com.wanxian.spring.context;

public abstract class AbstractApplicationContext {
    protected void onRefresh(){

    }

    protected abstract void refreshBeanFactory();

}
