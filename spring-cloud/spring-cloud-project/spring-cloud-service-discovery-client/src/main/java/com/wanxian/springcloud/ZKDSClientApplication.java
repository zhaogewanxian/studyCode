package com.wanxian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //尽可能使用 EnableDiscoveryClient 因为服务注册中心可能是eureka,zk
public class ZKDSClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZKDSClientApplication.class, args);
    }
}
