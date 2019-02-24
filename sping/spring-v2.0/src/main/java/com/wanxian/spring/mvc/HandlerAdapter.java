package com.wanxian.spring.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerAdapter {
    public ModleAndView handle(HttpServletRequest request, HttpServletResponse response, HandlerMapping handler) {
        //根据用户请求的参数信息，跟method中参数信息进行动态匹配
        //response :将其赋值给参数
        return null;
    }
}
