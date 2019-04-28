package com.wanxian.controller;

import com.wanxian.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
public class PersonController implements EnvironmentAware {
    @Autowired
    private Person person;
    /**
     * 2.注解 annotation
     */
    @Value("${person.id:6}")
    private Long id;
    @Value("${person.name:wanxian1}") //存在返回，不存在返回默认值
    private String name;

    private Integer age;

    @RequestMapping("wanxian")
    public Person getPerson() {
        return person;
    }


    @RequestMapping("personData")
    public Map<String, Object> personData() {
        Map<String, Object> data =new HashMap<>();
        data.put("id",id);
        data.put("name",name);
        data.put("age",age);
        return data;
    }

    /**
     * 3.java code
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.age= environment.getProperty("person.age",Integer.class);
    }
}
