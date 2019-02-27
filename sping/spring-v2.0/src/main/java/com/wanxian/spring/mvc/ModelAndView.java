package com.wanxian.spring.mvc;

import java.util.Map;

public class ModelAndView {


    private String viewName;
    private Map<String,?> model;

    public ModelAndView(String viewName, Map<String, ?> modle) {
        this.viewName = viewName;
        this.model = modle;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModle() {
        return model;
    }

    public void setModle(Map<String, ?> modle) {
        this.model = modle;
    }
}
