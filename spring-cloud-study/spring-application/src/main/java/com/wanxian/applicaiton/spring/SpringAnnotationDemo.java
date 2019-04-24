package com.wanxian.applicaiton.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * spring 模式注解
 * @Component 的"派生性" (注解没有extends)  @controller @service逻辑上区分，都是spring bean
 * @see Configuration
 * @see Component
 */
@Configuration
public class SpringAnnotationDemo {

    public static void main(String[] args) {
        /**
         *
         * Annotation 驱动
         * 找BeanDefinition
         * @Bean @Configuration
         */
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SpringAnnotationDemo.class);//注册
        applicationContext.refresh(); //启动上下文
        System.out.println(applicationContext.getBean(SpringAnnotationDemo.class));
    }
}
