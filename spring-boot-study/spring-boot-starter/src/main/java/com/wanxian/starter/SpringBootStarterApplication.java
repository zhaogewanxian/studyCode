package com.wanxian.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 不写spring.factories也可以扫描装配到 因为SpringBootApplication->ComponentScan
 */
@SpringBootApplication
public class SpringBootStarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterApplication.class, args);
    }
}
