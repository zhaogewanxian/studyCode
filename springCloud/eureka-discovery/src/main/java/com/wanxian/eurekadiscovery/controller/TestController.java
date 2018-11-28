package com.wanxian.eurekadiscovery.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    DiscoveryClient discoveryClient;
    @RequestMapping("test")
    public String test(){
        System.out.print("service:"+discoveryClient.getServices());
        String msg = "service:"+discoveryClient.getServices()+" host:8080";
        return msg;
    }

}
