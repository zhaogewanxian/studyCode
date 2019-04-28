package com.wanxian.configuration;

import com.wanxian.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class PersonConfiguration {

    @Bean
    @Profile("prod")
    public Person zhangxuyou() {
        Person person = new Person();
        return person;
    }
    @Bean
    @Profile("test")
    public Person zhangsan() {
        Person person = new Person();
        return person;
    }
}
