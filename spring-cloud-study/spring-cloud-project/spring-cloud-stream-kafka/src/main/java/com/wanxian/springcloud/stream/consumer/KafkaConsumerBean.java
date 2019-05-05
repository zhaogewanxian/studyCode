package com.wanxian.springcloud.stream.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 消息消费者Bean
 * 三种方式消费，三种共同存在时，执行三次，先后顺序为：1.@StreamListener 2.@ServiceActivator 3.@PostConstruct
 */
@Component
@EnableBinding({Sink.class})
public class KafkaConsumerBean {


    @Autowired
    @Qualifier(Sink.INPUT) //bean名称
    private SubscribableChannel subscribableChannel;
    @Autowired
    private Sink sink;

    /**
     * 当字段注入完成后进行回调
     * 1.异步监听
     */
    @PostConstruct
    public void init() {
        subscribableChannel.subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("subscribe:" + message.getPayload());
            }
        });
    }

    /**
     * 第二种方式 @ServiceActivator
     *
     * @param message
     */
    @ServiceActivator(inputChannel = Sink.INPUT)
    public void message(Object message) {
        System.out.println("@ServiceActivator:" + message);
    }

    /**
     * 第二种方式 @StreamListener
     *
     * @param message
     */
    @StreamListener(Sink.INPUT)
    public void kafkaConsumerListener(String message) {
        System.out.println("@StreamListener:" + message);
    }

}
