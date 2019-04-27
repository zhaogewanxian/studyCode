package com.wanxian.springcloud.service;

import com.wanxian.springcloud.annotation.RestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestClient(name = "spring-cloud-server-application")
public interface SayingRestService {

    @GetMapping("say")
    String saying(@RequestParam("message") String message);
}