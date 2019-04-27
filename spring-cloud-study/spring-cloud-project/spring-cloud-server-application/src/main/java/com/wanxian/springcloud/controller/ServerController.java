package com.wanxian.springcloud.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wanxian.springcloud.web.mvc.CircuitBreakerControllerAdvic;
import com.wanxian.springcloud.web.mvc.CircuitBreakerInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;


@RestController
public class ServerController {
    private final static Random random = new Random();
    private final static ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 注解方式
     * 当{@link #say} 方法调用超时或失败时，
     * fallback 方法{@link #errorContent(String)}}作为替代返回
     *
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "errorContent",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "100")
            }
    )

    @GetMapping("say")
    public String say(@RequestParam String message) throws InterruptedException {
        int value = random.nextInt(200);
        //>100 返回超时内容
        Thread.sleep(value);
        return "Hello," + message;
    }


    public String errorContent(String message) {
        return "fallback";
    }

    /**
     * 简易版本
     *
     * @param message
     * @return
     * @throws Exception
     */
    @GetMapping("say2")
    public String say2(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return doSay2(message);
        });
        String result = "";
        //100毫秒超时
        try {
            result = future.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            result = errorContent(message);
        }
        return result;
    }

    /**
     * 中级版本(1.拦截器，判断方法名称和异常类型，返回熔断内容2.全局异常统一处理)
     *
     * @param message
     * @return
     * @throws Exception
     * @see CircuitBreakerInterceptor
     */
    @GetMapping("middle/say")
    public String middleSay(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return doSay2(message);
        });
        String result = "";
        try {
            result = future.get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true); //取消执行，运行时是不是中断
            throw e; //重抛异常
        }
        return result;
    }

    /**
     * 高级版本

     *
     * @param message
     * @return
     * @throws Exception
     * @see CircuitBreakerControllerAdvic
     */
    @GetMapping("advance/say")
    public String advanceSay(@RequestParam String message) throws Exception {
        return doSay2(message);
    }

    private String doSay2(String message) throws InterruptedException {
        int value = random.nextInt(200);
        //>100 返回超时内容
        Thread.sleep(value);
        System.out.println("doSay2 cost:" + value);
        String result = "say2:" + message;
        return result;
    }


}
