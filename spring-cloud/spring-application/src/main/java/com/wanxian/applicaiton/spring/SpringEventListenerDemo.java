package com.wanxian.applicaiton.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.support.GenericApplicationContext;

public class SpringEventListenerDemo {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        //不指定事件类型，默认监听所有
//        context.addApplicationListener(event -> {
//            System.err.println("接收到事件：" + event);
//        });
        //添加自定义监听器 可以对某种事件监听
        context.addApplicationListener(new closeListener());
        context.refresh();
        //发布事件
        /**
         * 1.ContextRefreshedEvent
         * 2.PayloadApplicationEvent
         */
        context.publishEvent("hello world");
        /**
         * 1.myEvent
         */
        context.publishEvent(new myEvent("hello world by myEvent"));
        context.close();
    }


    private static class closeListener implements ApplicationListener<ContextClosedEvent> {

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            System.err.println("关闭上下文：" + event);
        }
    }

    private static class myEvent extends ApplicationEvent {

        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public myEvent(Object source) {
            super(source);
        }
    }
}
