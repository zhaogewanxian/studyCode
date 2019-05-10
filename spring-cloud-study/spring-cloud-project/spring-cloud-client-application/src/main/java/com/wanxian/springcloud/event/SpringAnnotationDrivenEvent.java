package com.wanxian.springcloud.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;

/**
 * spring 注解驱动事件
 */
public class SpringAnnotationDrivenEvent {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringAnnotationDrivenEvent.class);
        context.refresh();//启动上下文
        //等待上下文启动完成后发布事件
        context.publishEvent(new MyApplicationEvent("hello,world"));
        context.close();
    }

    private static class MyApplicationEvent extends ApplicationEvent {

        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public MyApplicationEvent(Object source) {
            super(source);
        }
    }

//    @EventListener
//    public void onMessage(MyApplicationEvent event) {
//        System.err.println("监听到事件:" + event);
//    }
    @EventListener
    public void onMessage(Object event) {
        System.err.println("监听到事件:" + event);
    }


}
