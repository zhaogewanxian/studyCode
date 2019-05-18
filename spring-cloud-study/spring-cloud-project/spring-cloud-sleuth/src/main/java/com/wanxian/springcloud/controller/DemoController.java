package com.wanxian.springcloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("")
    public String demo() {
        String result = "Hello,World";
        logger.info("{} demo():{} " + getClass().getSimpleName() + result);
        return result;
    }

    @GetMapping("/to")
    public Object toZuul() {
        return "";
    }

}
