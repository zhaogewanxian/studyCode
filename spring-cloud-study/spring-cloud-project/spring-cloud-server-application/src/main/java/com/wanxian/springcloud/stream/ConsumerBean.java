package com.wanxian.springcloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * 消息消费者
 * 三种监听方式共同存在时候，执行顺序为：@StreamListener->@ServiceActivator->@PostConstruct
 */
@Component
@EnableBinding({WanxianMessageSink.class})
public class ConsumerBean {
    @Autowired
    private WanxianMessageSink wanxianMessageSink;

    /**
     * 当字段注入完成后
     * 异步监听
     * 接口编程方式
     */
    @PostConstruct
    public void init() {
        wanxianMessageSink.wanxian().subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                MessageHeaders messageHeaders = message.getHeaders();
                String encoding = (String) messageHeaders.get("charset-encoding");
                byte[] content = (byte[]) message.getPayload();
                try {
                    System.out.println("@PostConstruct:" + new String(content, encoding));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @param message
     * @StreamListener spring cloud 注解驱动
     */
    @StreamListener("wanxian")
    public void init(String message) {
        System.out.println("@StreamListener:" + message);
    }

    /**
     * Spring Integration 注解驱动
     *
     * @param message
     */
    @ServiceActivator(inputChannel = "wanxian")
    public void serviceActivator(String message) {
        System.out.println("@ServiceActivator:" + message);
    }
}
