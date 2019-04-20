package com.wanxian.springcloud.controller;

import com.wanxian.springcloud.loadbalance.LoadBalanceRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SayController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${spring.application.name}")
    private String currentServices;
    private volatile Set<String> serviceNames = new HashSet<>();

//    private volatile Map<String, Set<String>> targetUrlsCache = new HashMap<>();
//
//    @Scheduled(fixedDelay = 10 * 1000)
//    public void updateTargetUrls() {
//        Map<String, Set<String>> newTargetUrlsCache = new HashMap<>();
//        discoveryClient.getServices().forEach(serviceName -> {
//            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
//            Set<String> newTargetUrl = serviceInstances.stream().map(s ->
//                    s.isSecure() ? "https://" + s.getHost() + ":" + s.getPort() :
//                            "http://" + s.getHost() + ":" + s.getPort()
//            ).collect(Collectors.toSet());
//            newTargetUrlsCache.put(serviceName, newTargetUrl); //本地缓存，防止注册中心挂
//        });
//        //swap
//        this.targetUrlsCache = newTargetUrlsCache;
//    }
//    @Scheduled(fixedDelay = 10 * 1000)
//    public void updateServices() {
//        Set<String> oldServiceNames = this.serviceNames;
//        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(currentServices);
//        Set<String> newServiceNames = serviceInstanceList.stream().map(s ->
//                s.isSecure() ? "https://" + s.getHost() + ":" + s.getPort() :
//                        "http://" + s.getHost() + ":" + s.getPort()
//        ).collect(Collectors.toSet());
//        //swap
//        this.serviceNames = newServiceNames;
//        oldServiceNames.clear();
//    }


    @GetMapping("invoke/{serviceName}/say")
    public String invokeSay(@PathVariable String serviceName, @RequestParam String message) {
        //在restTemplate 中进行算法处理
        return restTemplate.getForObject("/" + serviceName + "/say?message=" + message, String.class);
    }

//    @GetMapping("invoke/say")
//    public String invokeSay(@RequestParam String message) {
//        List<String> urls = new ArrayList<>(this.serviceNames);
//        int size = serviceNames.size();
//        int index = new Random().nextInt(size);
//        String url = urls.get(index);
//        return restTemplate.getForObject(url + "/say?message=" + message, String.class);
//    }
//
//    @GetMapping("say")
//    public String say(@RequestParam String message) {
//        return "Hello," + message;
//    }

    @Bean
    public LoadBalanceRequestInterceptor interceptor() {
        return new LoadBalanceRequestInterceptor();
    }

    @Bean
    @Autowired
    public RestTemplate restTemplate(LoadBalanceRequestInterceptor interceptor) {
        RestTemplate restTemplate = new RestTemplate();
        //增加拦截器
        restTemplate.setInterceptors(Arrays.asList(interceptor));
        return restTemplate;
    }

}
