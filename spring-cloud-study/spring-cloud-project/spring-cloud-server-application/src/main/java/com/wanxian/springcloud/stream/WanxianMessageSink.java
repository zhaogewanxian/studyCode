package com.wanxian.springcloud.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;


/**
 * 自定义实现
 * @see Sink
 */
public interface WanxianMessageSink {
    String channelName = "wanxian";

    @Input(channelName)
    SubscribableChannel wanxian();
}