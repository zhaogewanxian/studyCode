package com.wanxian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class SpringCloudConfigServer {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServer.class, args);
    }

    /**
     * {@link EnvironmentRepository}
     * 自定义实现(当spring上下文出现 EnvironmentRepository bean时,DefaultRepositoryConfiguration 默认将会失效)
     * (bootstrap Application 或者 Application)Environment ->List<PropertySource>->PropertySource
     * @return
     */
    //@Bean
    public EnvironmentRepository environmentRepository() {
        return (String application, String profile, String label) -> {
            Environment environment = new Environment("default", profile);
            List<PropertySource> propertySourceList = environment.getPropertySources();
            Map<String, Object> map = new HashMap<>();
            map.put("name", "晚弦");
            PropertySource propertySource = new PropertySource("myPropertySource", map);
            propertySourceList.add(propertySource);
            return environment;
        };
    }


}
