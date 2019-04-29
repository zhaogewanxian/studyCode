package com.wanxian.starter.controller;

import com.wanxian.starter.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final Person person;

    /**
     * 构造器注入方式
     *
     * @param person
     */
    @Autowired
    public PersonController(Person person) {
        this.person = person;
    }

    @GetMapping("person")
    public Person getPerson() {
        return person;
    }
}
