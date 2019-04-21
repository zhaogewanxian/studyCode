package com.wanxian.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class SpringCloudServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudServerApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
