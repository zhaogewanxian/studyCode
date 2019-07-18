package com.wanxian.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringApplicationBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringApplicationBootstrap.class).build().run(args);
    }
}
