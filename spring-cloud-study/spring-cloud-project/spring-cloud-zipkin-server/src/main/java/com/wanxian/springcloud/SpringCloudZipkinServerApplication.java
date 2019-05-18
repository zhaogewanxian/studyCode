package com.wanxian.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import zipkin.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class SpringCloudZipkinServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudZipkinServerApplication.class).run(args);
    }
}
