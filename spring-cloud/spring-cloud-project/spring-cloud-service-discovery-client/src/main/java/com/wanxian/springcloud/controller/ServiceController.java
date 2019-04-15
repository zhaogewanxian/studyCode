package com.wanxian.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ServiceController {
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 返回所有服务名称
     *
     * @return
     */
    @RequestMapping("all")
    public List<String> getAllService() {
        return discoveryClient.getServices();
    }

    /**
     * 返回所有服务实例
     *
     * @return
     */
    @RequestMapping("/service/instances/{serviceName}")
    public List<String> getServiceInstances(@PathVariable String serviceName) {
        return discoveryClient.getInstances(serviceName).stream().map(s -> {
            return s.getServiceId() + " - " + s.getHost() + ":" + s.getPort();
        }).collect(Collectors.toList());
    }
}
