package com.wanxian.applicaiton;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableAutoConfiguration
public class SpringBootEventDemo {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootEventDemo.class).listeners(event -> {
            System.err.println("接收到事件:" + event.getClass().getName());
        }).run(args).close();
    }
}
