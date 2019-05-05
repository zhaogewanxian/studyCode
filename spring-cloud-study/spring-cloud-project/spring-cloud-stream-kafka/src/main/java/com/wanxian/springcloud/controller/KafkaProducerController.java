package com.wanxian.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * kafka生产者控制器
 */
@RestController
public class KafkaProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    @Autowired
    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate, @Value("${kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @PostMapping("sendMessage")
    public Boolean sendMessage(@RequestParam String message) {
        kafkaTemplate.send(topic, message);
        return true;
    }
}
