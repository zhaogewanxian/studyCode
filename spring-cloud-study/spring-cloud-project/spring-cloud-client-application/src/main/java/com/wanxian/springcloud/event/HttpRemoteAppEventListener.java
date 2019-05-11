package com.wanxian.springcloud.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link RemoteAppEvent} 监听器,通过http将事件发送到服务端
 */
public class HttpRemoteAppEventListener implements SmartApplicationListener {
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private DiscoveryClient discoveryClient;
    private String currentAppName; //HttpRemoteAppEventListener不是bean 无法注入

    public void onApplicationEvent(RemoteAppEvent event) {
        Object source = event.getSource();
        String appName = event.getAppName();
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(appName);
        //拼接url
        for (ServiceInstance s : serviceInstanceList) {
            String url = s.isSecure() ? "https://" + s.getHost() + ":" + s.getPort() :
                    "http://" + s.getHost() + ":" + s.getPort();
            Map<String, Object> data = new HashMap<>();
            url = url + "/receiver/remote/event";
            data.put("sender", currentAppName);
            data.put("value", source);
            data.put("type", RemoteAppEvent.class.getName());
            //发送http请求到服务端
            String result = restTemplate.postForObject(url, data, String.class);
        }
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) { //判断拦截事件
        return RemoteAppEvent.class.isAssignableFrom(eventType)
                || ContextRefreshedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {//判断事件类型
        if (event instanceof RemoteAppEvent) {
            onApplicationEvent((RemoteAppEvent) event);

        } else if (event instanceof ContextRefreshedEvent) {
            onContextRefreshedEvent((ContextRefreshedEvent) event);
        }

    }

    private void onContextRefreshedEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        this.discoveryClient = applicationContext.getBean(DiscoveryClient.class);
        this.currentAppName = applicationContext.getEnvironment().getProperty("spring.application.name");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
