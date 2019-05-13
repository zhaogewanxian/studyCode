package com.wanxian.springcloud.controller;

import com.wanxian.springcloud.event.RemoteAppEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class RemoteAppSendEventController implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${spring.application.name}")
    private String currentAppName;

    @GetMapping("send/remote/event")
    public String sendEvent(String message) {
        publisher.publishEvent(message);
        return "sent";
    }

//    /**
//     * 集群发送
//     *
//     * @param appName
//     * @param data
//     * @return
//     */
//    @GetMapping("send/remote/event/{appName}")
//    public String sendAppCluster(@PathVariable String appName, @RequestParam String message) {
//        //客户端发送数据给自己,监听器 发送到server端
//        //根据应用名称获取应用实例
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(appName);
//        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(message, serviceInstances, appName, currentAppName);
//        //发送事件
//        publisher.publishEvent(remoteAppEvent);
//        return "sent";
//    }

    /**
     * 集群发送
     *
     * @param appName
     * @param
     * @return
     */
    @GetMapping("send/remote/event/{appName}")
    public String sendAppCluster(@PathVariable String appName, @RequestParam String message) {
        //客户端发送数据给自己,监听器 发送到server端
        //根据应用名称获取应用实例
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(appName);
        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(message, appName, true);
        //发送事件
        publisher.publishEvent(remoteAppEvent);
        return "sent";
    }

//    /**
//     * 单台发送
//     *
//     * @param appName
//     * @param data
//     * @return
//     */
//
//    @GetMapping("send/remote/event/{appName}/{ip}/{port}")
//    public String sendAppInstance(@PathVariable String appName,
//                                  @PathVariable String ip,
//                                  @PathVariable int port,
//                                  @RequestBody Object data) {
//        //客户端发送数据给自己,监听器 发送到server端
//        //根据应用名称获取应用实例
//        ServiceInstance serviceInstance = new DefaultServiceInstance(appName, ip, port, false);
//        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(data, Arrays.asList(serviceInstance), appName, currentAppName);
//        //发送事件
//        publisher.publishEvent(remoteAppEvent);
//        return "sent";
//    }

    /**
     * 单台发送
     *
     * @param appName
     * @param ip
     * @param port
     * @param message
     * @return
     */

    @GetMapping("send/remote/event/{appName}/{ip}/{port}")
    public String sendAppInstance(@PathVariable String appName,
                                  @PathVariable String ip,
                                  @PathVariable int port,
                                  @RequestParam String message) {
        //客户端发送数据给自己,监听器 发送到server端
        //根据应用名称获取应用实例
        ServiceInstance serviceInstance = new DefaultServiceInstance(appName, ip, port, false);
        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(message, appName, false);
        //发送事件
        publisher.publishEvent(remoteAppEvent);
        return "sent";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
