package com.wanxian.springcloud.controller;

import com.wanxian.springcloud.stream.producer.MessageProducerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final MessageProducerBean messageProducerBean;

    @Autowired
    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate, @Value("${kafka.topic}") String topic, MessageProducerBean messageProducerBean) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.messageProducerBean = messageProducerBean;
    }


    /**
     * 通过kafkaTemplate发送
     * {@link KafkaTemplate}
     *
     * @param message
     * @return
     */
    @PostMapping("sendMessage")
    public Boolean sendMessage(@RequestParam String message) {
        kafkaTemplate.send(topic, message);
        return true;
    }

    /**
     * 通过消息生产者发送
     * {@link MessageProducerBean}
     *
     * @param message
     * @return
     */
    @GetMapping("sendMessage")
    public Boolean send(@RequestParam String message) {
        messageProducerBean.message(message);
        return true;
    }

    /**
     * 通过消息生产者发送
     * {@link MessageProducerBean}
     *
     * @param message
     * @return
     */
    @GetMapping("sendToWanXian")
    public Boolean sendToWanXian(@RequestParam String message) {
        messageProducerBean.onWanxianMessage(message);
        return true;
    }
}
