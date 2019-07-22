package com.wanxian.springboot.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;

public class AfterApplicationListener implements ApplicationListener<ContextRefreshedEvent>, Order {
    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("AfterApplicationListener : " + contextRefreshedEvent.getApplicationContext() +
                ",timestamp:" + contextRefreshedEvent.getTimestamp());
    }

    @Override
    public int value() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
