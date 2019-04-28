package com.wanxian.context;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @see ApplicationEnvironmentPreparedEvent
 */
public class MySpringBootApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment configurableEnvironment = event.getEnvironment();
        MutablePropertySources mutablePropertySources = configurableEnvironment.getPropertySources();
        Map<String, Object> map = new HashMap<>();
        map.put("server.port", 6789);//设置端口
        PropertySource propertySource = new MapPropertySource("java-code", map);
        mutablePropertySources.addFirst(propertySource); //添加到第一位
    }
}
