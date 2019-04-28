package com.wanxian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
/**
 * 1.xml
 * 定义两个xml不会报错，后者将前者覆盖
 */
@ImportResource(locations = {"/META-INF/spring/context.xml",
        "/META-INF/spring/context-test.xml",
        "/META-INF/spring/context-prod.xml"})
public class SpringBootEnvironmentApplication implements EnvironmentAware {
    public static void main(String[] args)
    {
       SpringApplication springApplication =new  SpringApplication(SpringBootEnvironmentApplication.class);
       springApplication.setAdditionalProfiles("prod");
       springApplication.run(args);
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.err.println("当前激活的profiles:"+ Arrays.asList(environment.getActiveProfiles()));
        if (environment instanceof ConfigurableEnvironment){
            ConfigurableEnvironment configurableEnvironment = ConfigurableEnvironment.class.cast(environment);
            MutablePropertySources mutablePropertySources = ((ConfigurableEnvironment) environment).getPropertySources();
            Map<String,Object> map = new HashMap<>();
            map.put("server.port",12345);//设置端口
            PropertySource propertySource =new MapPropertySource("java-code",map);
            mutablePropertySources.addFirst(propertySource); //添加到第一位
        }
    }
}
