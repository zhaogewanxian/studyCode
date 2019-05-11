package com.wanxian.springcloud.controller;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 远程事件接收器 控制器
 */
@RestController
public class RemoteAppEventReceiverController implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @PostMapping("/receiver/remote/event")
    public String receiver(@RequestBody Map<String, Object> data) { //rest 请求不需要指定参数类型
        String sender = (String) data.get("sender");
        Object value = data.get("value");
        publisher.publishEvent(new SenderRemoteEvent(value, sender));
        return "receiver";
    }

    public static class SenderRemoteEvent extends ApplicationEvent {
        private String sender;

        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public SenderRemoteEvent(Object source, String sender) {
            super(source);
            this.sender = sender;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
    }

    @EventListener
    public void onEvent(SenderRemoteEvent event) {
        System.out.println("接收到事件:" + event.getSource() + ",sender:" + event.getSender());
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
