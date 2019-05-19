package com.wanxian.springcloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public DemoController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    public String demo() {
        String result = "Hello,World";
        logger.info("{} demo():{} " + getClass().getSimpleName() + result);
        return result;
    }

    @GetMapping("/to/zuul/feign/say")
    public Object toZuul(String message) {
        return restTemplate.getForObject("http://spring-cloud-zuul/client-application/feign/say?message=" + message,
                Object.class);
    }


}
