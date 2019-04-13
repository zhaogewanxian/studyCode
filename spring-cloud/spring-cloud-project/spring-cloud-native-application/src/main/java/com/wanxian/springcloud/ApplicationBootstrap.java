package com.wanxian.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
public class ApplicationBootstrap {
    public static void main(String[] args) {
//        SpringApplication springApplication = new SpringApplication(ApplicationBootstrap.class);
//        springApplication.run(args);
        //SpringApplication.run(ApplicationBootstrap.class, args);
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.setId("晚弦");
        //在晚弦 注册一个helloWorld的bean
        annotationConfigApplicationContext.registerBean("helloWorld", String.class, "HelloWorld");
        annotationConfigApplicationContext.refresh();
        new SpringApplicationBuilder(ApplicationBootstrap.class).parent(annotationConfigApplicationContext).run(args);
    }

    @Autowired
    @Qualifier(value = "helloWorld")
    private String name;

    @RequestMapping("name")
    public String getName() {
        return name;
    }

}
