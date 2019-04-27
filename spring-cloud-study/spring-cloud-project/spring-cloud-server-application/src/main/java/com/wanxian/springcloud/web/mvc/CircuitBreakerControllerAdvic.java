package com.wanxian.springcloud.web.mvc;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeoutException;

/**
 * @see RestControllerAdvice
 * @see ExceptionHandler
 */
@RestControllerAdvice
public class CircuitBreakerControllerAdvic {

    @ExceptionHandler
    public void onTimeoutException(TimeoutException exception, Writer writer) throws IOException {
        writer.write(errorContent(""));
    }

    public String errorContent(String message) {
        return "fallback";
    }
}
