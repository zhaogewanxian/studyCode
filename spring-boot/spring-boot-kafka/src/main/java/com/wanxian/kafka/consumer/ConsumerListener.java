package com.wanxian.kafka.consumer;

import com.wanxian.kafka.entity.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerListener {
    @KafkaListener(topics = "wanxian2")
    public void consumer(String message) {
        System.out.println(message);
    }
    @KafkaListener(topics = "wanxian-user")
    public void consumer(User user) {
        System.out.println(user);
    }
}
