package com.wanxian;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAnnotationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SpringAnnotationDemo.class);//注册
        applicationContext.refresh(); //启动上下文
        System.out.println(applicationContext.getBean(SpringAnnotationDemo.class));
    }
}
