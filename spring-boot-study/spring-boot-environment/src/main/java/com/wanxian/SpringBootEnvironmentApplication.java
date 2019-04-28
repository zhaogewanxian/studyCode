package com.wanxian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
/**
 * 1.xml
 * 定义两个xml不会报错，后者将前者覆盖
 */
@ImportResource(locations = {"/META-INF/spring/context.xml","/META-INF/spring/context-test.xml"})
public class SpringBootEnvironmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootEnvironmentApplication.class, args);
    }
}
