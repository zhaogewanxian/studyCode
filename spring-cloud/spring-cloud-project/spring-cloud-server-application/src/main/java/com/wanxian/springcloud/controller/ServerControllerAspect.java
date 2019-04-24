package com.wanxian.springcloud.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * serverController 切面
 */

@Aspect
@Component
public class ServerControllerAspect {
    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Around("execution(* com.wanxian.springcloud.controller.ServerController.advanceSay(..))&&args(message)")
    public Object advancedSayInTimeout(ProceedingJoinPoint point, String message) throws Throwable {

        Future<Object> future = executorService.submit(() -> {
            Object returnObject = null;
            try {
                point.proceed(new Object[]{message});
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return returnObject;
        });
        Object returnObject = null;
        try {
            returnObject = future.get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true); //取消执行，运行时是不是中断
            returnObject = errorContent("");
        }
        return returnObject;

    }

    public String errorContent(String message) {
        return "fallback";
    }

    @PreDestroy
    private void destroy() {
        executorService.shutdown(); //关闭线程池
    }
}
