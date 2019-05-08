package com.wanxian.springcloud.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * {@link Source}
 * 自定义实现
 */
public interface WanxianMessageSource {
    String channalName = "wanxian";

    @Output(channalName)
    MessageChannel wanxian();
}
