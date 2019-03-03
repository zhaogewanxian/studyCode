package com.wanxian.demo.aspect;

public class LogAspect {
    public  void before(){
        System.out.printf("Invoker Before Method");
    }
    public  void after(){
        System.out.printf("Invoker After Method");
    }
}
