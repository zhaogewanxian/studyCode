package com.wanxian.nacos.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wanxian.nacos.service.DemoService;

@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoService {
    public String demo(String message) {
        return "demo" + message;
    }
}
