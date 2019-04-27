package com.wanxian.springcloud;

import com.wanxian.springcloud.annotation.EnableRestClient;
import com.wanxian.springcloud.service.feign.client.SayingService;
import com.wanxian.springcloud.service.SayingRestService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableFeignClients(clients = SayingService.class)
@EnableRestClient(clients = SayingRestService.class)
public class SpringCloudClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudClientApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
