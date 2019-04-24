package com.wanxian.jpa.service;

import com.wanxian.jpa.entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service //spring 模式注解
public class UserService {
    @PersistenceContext
    private EntityManager entityManager; //实体管理器

    @Transactional
    public void add(User user) {
        entityManager.persist(user); //insert
    }

    public User getOne(long id) {
        return entityManager.find(User.class, id);//select
    }

}
