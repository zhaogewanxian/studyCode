package com.wanxian.springcloud.web.mvc;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.concurrent.TimeoutException;

public class CircuitBreakerInterceptor implements HandlerInterceptor {
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
//        if ("middle/say".equals(request.getRequestURI()) && ex instanceof TimeoutException) {
//            Writer writer = response.getWriter();
//            writer.write(errorContent(""));
//        }

    }

    public String errorContent(String message) {
        return "fallback";
    }
}


