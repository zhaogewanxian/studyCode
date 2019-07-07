package com.wanxian.nacos.bootstrap;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.wanxian.nacos.service.DemoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.io.IOException;

@EnableDubbo
//@PropertySource(value = "classpath:/consumer.properties")
@EnableNacosConfig //激活Nacos分布式配置
@NacosPropertySource(dataId = "dubbo-consumer-demo") //Nacos 配置管理中Data Id
public class ConsumerBootstrap {
    static {
        System.setProperty("nacos.server-addr", "127.0.0.1:8848");
    }


    @Reference(version = "${demo.service.version}")
    private DemoService demoService;

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            System.out.println(demoService.demo("你好，晚弦" + i));
        }
    }

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ConsumerBootstrap.class);
        applicationContext.refresh();
        System.out.println("消费者启动完成.......");
        System.in.read();

    }
}
