package com.wanxian.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudSleuthApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudSleuthApplication.class).run(args);
    }
}
