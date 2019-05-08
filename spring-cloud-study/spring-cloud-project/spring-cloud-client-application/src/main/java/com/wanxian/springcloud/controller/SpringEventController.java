package com.wanxian.springcloud.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringEventController implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @GetMapping("sendEvent")
    public String sendEvent(String message) {
        publisher.publishEvent(message);
        return "sent";
    }

    @EventListener
    public void onMessage(String message) {
        System.out.println("监听到消息:" + message);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
