package com.wanxian.applicaiton;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Component 的"派生性" (注解没有extends)
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
