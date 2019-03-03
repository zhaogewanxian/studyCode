package com.wanxian.spring.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AopConfig {

    private Map<Method, Aspect> points = new HashMap<Method, Aspect>();

    public void put(Method target, Object aspect, Method[] points) {
        this.points.put(target, new Aspect(aspect, points));
    }

    public Aspect get(Method target) {
        return this.points.get(target);
    }

    public boolean contanins(Method target) {
        return this.points.containsKey(target);
    }

    public class Aspect {
        private Object aspect;
        private Method[] points;

        public Aspect(Object aspect, Method[] points) {
            this.aspect = aspect;
            this.points = points;
        }

        public Object getAspect() {
            return aspect;
        }

        public Method[] getPoints() {
            return points;
        }
    }
}
