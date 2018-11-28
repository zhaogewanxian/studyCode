package com.wanxian.ribbonconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
    @Autowired
    RestTemplate restTemplate;


    @RequestMapping("consumer")
    public String consumer(){
        return restTemplate.getForEntity("http://provide/test",String.class).getBody();
    }
}
