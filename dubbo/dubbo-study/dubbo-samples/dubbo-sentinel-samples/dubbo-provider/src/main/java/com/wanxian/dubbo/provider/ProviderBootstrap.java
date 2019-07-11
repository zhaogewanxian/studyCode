package com.wanxian.dubbo.provider;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableAutoConfiguration
public class ProviderBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ProviderBootstrap.class)
                .build().run(args);

    }
}
