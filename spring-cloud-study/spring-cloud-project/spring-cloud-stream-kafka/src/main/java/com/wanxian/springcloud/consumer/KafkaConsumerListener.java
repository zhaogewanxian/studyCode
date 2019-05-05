package com.wanxian.springcloud.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka消费者监听器
 */
@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = "${kafka.topic}")
    public void kafkaConsumerListener(String message) {
        System.out.println("接收到消息:" + message);
    }

    @KafkaListener(topics = "test")
    public void kafkaConsumerListenerByWanxian(String message) {
        System.out.println("接收到消息:" + message);
    }
}
