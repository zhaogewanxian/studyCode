package com.wanxian.applicaiton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringApplicationDemo {
    public static void main(String[] args) {
        /**
         * 第一种写法
         */
        //SpringApplication.run(SpringApplicationDemo.class, args);
        /**
         * Fluent API
         * 第二种写法
         * server.port=0 随机向os获取一个可用的端口
         */
        new SpringApplicationBuilder(SpringApplicationDemo.class)
                .properties("server.port=0").run(args);
    }
}
