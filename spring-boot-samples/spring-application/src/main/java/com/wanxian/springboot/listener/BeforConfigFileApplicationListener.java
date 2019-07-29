package com.wanxian.springboot.listener;

import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;

public class BeforConfigFileApplicationListener implements SmartApplicationListener, Ordered {
    @Override
    public int getOrder() {
        return ConfigFileApplicationListener.DEFAULT_ORDER + 1; //order比ConfigFileApplicationListener中order小，无法获取到name值
    }


    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(eventType)
                || ApplicationPreparedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            System.out.println("getEnvironment().getProperty(\"name\"):"
                    + ((ApplicationEnvironmentPreparedEvent) event)
                    .getEnvironment().getProperty("name"));//从Environment中获取配置文件中name值
        }
        if (event instanceof ApplicationPreparedEvent) {
        }
    }
}
