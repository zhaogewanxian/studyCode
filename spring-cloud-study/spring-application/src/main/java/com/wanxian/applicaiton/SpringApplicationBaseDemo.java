package com.wanxian.applicaiton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基本写法
 */
@SpringBootApplication
public class SpringApplicationBaseDemo {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringApplicationBaseDemo.class);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("server.port", 0);
        //设置为非web应用,运行之后直接停止
        springApplication.setWebApplicationType(WebApplicationType.NONE); //spring boot 2.0.0
        springApplication.setDefaultProperties(map);
        springApplication.run(args);

    }

}
