package com.wanxian.spring.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

public class HandlerAdapter {
    private Map<String, Integer> paramMapping;

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, HandlerMapping handler) throws InvocationTargetException, IllegalAccessException {
        //根据用户请求的参数信息，跟method中参数信息进行动态匹配
        //response :将其赋值给参数
        Class<?>[] paramTypes = handler.getMethod().getParameterTypes();
        Map<String, String[]> reqParamValues = request.getParameterMap();

        Object[] paramValues = new Object[paramTypes.length];
        for (Map.Entry<String, String[]> param :
                reqParamValues.entrySet()) {
            String value = Arrays.toString(param.getValue());
            if (!this.paramMapping.containsKey(param)) continue;
            int index = this.paramMapping.get(param.getKey());

            paramValues[index] = caseStringValue(value, paramTypes[index]);

            if(this.paramMapping.containsKey(HttpServletRequest.class.getName())) {
                int reqIndex = this.paramMapping.get(HttpServletRequest.class.getName());
                paramValues[reqIndex] = request;
            }

            if(this.paramMapping.containsKey(HttpServletResponse.class.getName())) {
                int respIndex = this.paramMapping.get(HttpServletResponse.class.getName());
                paramValues[respIndex] = response;
            }

        }
        //4、从handler中取出controller、method，然后利用反射机制进行调用

        Object result = handler.getMethod().invoke(handler.getController(),paramValues);

        if(result == null){ return  null; }
        boolean isModelAndView = handler.getMethod().getReturnType() == ModelAndView.class;
        if(isModelAndView){
            return (ModelAndView)result;
        }else{
            return null;
        }

    }

    public HandlerAdapter(Map<String, Integer> paramMapping) {
        this.paramMapping = paramMapping;
    }

    private Object caseStringValue(String value, Class<?> clazz) {
        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == int.class) {
            return Integer.valueOf(value).intValue();
        } else {
            return null;
        }
    }
}
