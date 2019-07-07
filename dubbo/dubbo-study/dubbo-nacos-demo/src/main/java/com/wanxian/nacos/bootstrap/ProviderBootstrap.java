package com.wanxian.nacos.bootstrap;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@EnableDubbo(scanBasePackages = "com.wanxian.nacos.service")
@PropertySource(value = "classpath:/provider.properties")
public class ProviderBootstrap {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ProviderBootstrap.class);
        applicationContext.refresh();
        System.out.println("生产者启动完成.......");
        System.in.read();

    }



}
