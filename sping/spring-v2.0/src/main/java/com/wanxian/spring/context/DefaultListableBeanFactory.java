package com.wanxian.spring.context;

import com.wanxian.spring.beans.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractApplicationContext {
    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    @Override
    protected void refreshBeanFactory() {

    }

    protected void onRefresh() {

    }
}
