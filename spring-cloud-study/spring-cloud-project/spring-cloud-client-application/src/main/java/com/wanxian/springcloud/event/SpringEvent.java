package com.wanxian.springcloud.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link ApplicationEvent}
 * {@link ApplicationListener}
 * spring 事件
 */
public class SpringEvent {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Object.class);

        context.addApplicationListener(event -> {
            System.err.println("接收到事件：" + event.getClass().getSimpleName());
        });
        context.refresh();
        context.start();
        context.stop();
        context.close();
    }
}
