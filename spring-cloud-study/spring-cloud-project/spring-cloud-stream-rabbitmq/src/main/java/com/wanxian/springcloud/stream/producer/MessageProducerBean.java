package com.wanxian.springcloud.stream.producer;

import com.wanxian.springcloud.stream.messaging.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 消息生产者bean
 */
@Component
@EnableBinding({Source.class, MessageSource.class})
public class MessageProducerBean {

    @Autowired
    @Qualifier(Source.OUTPUT) //bean名称
    private MessageChannel messageChannel;

    @Autowired
    private Source source;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier(MessageSource.OUTPUT) // Bean 名称
    private MessageChannel wanxianMessageChannel;

    public void message(String message) {
        //通过消息管道发送消息
        //messageChannel.send(MessageBuilder.withPayload(message).build());
        //另外一种写法
        source.output().send(MessageBuilder.withPayload(message).build());
    }

    public void onWanxianMessage(String message) {
        wanxianMessageChannel.send(MessageBuilder.withPayload(message).build());
    }

}
