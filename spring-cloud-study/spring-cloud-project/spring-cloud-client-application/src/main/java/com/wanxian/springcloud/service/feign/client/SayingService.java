package com.wanxian.springcloud.service.feign.client;

import com.wanxian.springcloud.annotation.RestClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spring-cloud-server-application")
public interface SayingService {
    @RequestMapping("say")
    String saying(@RequestParam String message);
}
