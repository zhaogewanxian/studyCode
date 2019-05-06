package com.wanxian.springcloud.controller;

import com.wanxian.springcloud.stream.producer.MessageProducerBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * kafka生产者控制器
 */
@RestController
public class ProducerController {

    private final MessageProducerBean messageProducerBean;

    public ProducerController(MessageProducerBean messageProducerBean) {
        this.messageProducerBean = messageProducerBean;
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
