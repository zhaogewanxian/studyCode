package com.wanxian.springcloud.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ServerController {

    @GetMapping("say")
    public String say(@RequestParam String message) {
        return "Hello," + message;
    }


}
