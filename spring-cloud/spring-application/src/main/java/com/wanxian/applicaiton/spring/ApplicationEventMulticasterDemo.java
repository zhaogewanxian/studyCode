package com.wanxian.applicaiton.spring;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

public class ApplicationEventMulticasterDemo {
    public static void main(String[] args) {
        ApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        multicaster.addApplicationListener(event -> {
            System.out.println("接收到事件:" + event);
        });
        multicaster.multicastEvent(new PayloadApplicationEvent<>("1", "hello world"));
    }
}
