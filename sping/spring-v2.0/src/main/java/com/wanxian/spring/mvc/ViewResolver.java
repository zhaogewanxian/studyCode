package com.wanxian.spring.mvc;


import java.io.File;

//将静态文件变为动态文件
public class ViewResolver {
    private String name;
    private File template;

    public ViewResolver(String viewName) {

    }

    public ViewResolver(String name, File template) {
        this.name = name;
        this.template = template;
    }

    public String viewResolver(ModleAndView mv) {
        return null;
    }
}
