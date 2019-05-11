package com.wanxian.springcloud.event;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class RemoteAppEvent extends ApplicationEvent {
    /**
     * 类型 http、rpc、消息
     */
    private String type;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 是否广播到集群
     */
    private boolean isCluster;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source  the object on which the event initially occurred (never {@code null})
     * @param
     * @param appName
     */
    public RemoteAppEvent(Object source, String appName, boolean isCluster) {
        super(source);
        this.appName = appName;
        this.isCluster = isCluster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}
