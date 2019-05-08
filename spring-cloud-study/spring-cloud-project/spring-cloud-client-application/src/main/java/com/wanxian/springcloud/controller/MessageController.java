package com.wanxian.springcloud.controller;

import com.wanxian.springcloud.stream.WanxianMessageSource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private WanxianMessageSource wanXianMessageSource;

    @GetMapping("sendMessage")
    public String sendMessage(@RequestParam("message") String message) {
        rabbitTemplate.convertAndSend(message);
        return "OK";
    }

    /**
     * stream 自定义实现
     *
     * @param message
     * @return
     */
    @GetMapping("stream/sendMessage")
    public Boolean streamSendMessage(@RequestParam("message") String message) {
        MessageChannel messageChannel = wanXianMessageSource.wanxian();
        Map<String, Object> headers = new HashMap<>(); //设置请求头
        headers.put("charset-encoding", "UTF-8");
        headers.put("content-type", MediaType.TEXT_PLAIN_VALUE);
        return messageChannel.send(new GenericMessage(message, headers)); //设置请求体 payload
    }

}
