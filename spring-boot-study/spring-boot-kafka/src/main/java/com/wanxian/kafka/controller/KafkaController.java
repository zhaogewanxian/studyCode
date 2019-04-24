package com.wanxian.kafka.controller;

import com.wanxian.kafka.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class KafkaController {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaController(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/message/send")
    public String send(@RequestParam String message) {
        kafkaTemplate.send("wanxian2", 0, message);
        return message;
    }

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user) {
        kafkaTemplate.send("wanxian-uesr", 0, user);
        return user;
    }
}
