package com.wanxian.springcloud.stream.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * {@link Source}
 * 自定义消息发送源
 */
public interface MessageSource {

    /**
     * 消息来源的管道名称："wanxian"
     */
    String OUTPUT = "wanxian";

    @Output(OUTPUT)
    MessageChannel wanxian();

}
