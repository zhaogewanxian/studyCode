package com.wanxian.jpa.repository;

import com.wanxian.jpa.entity.User;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@Repository
public class UserRepository extends SimpleJpaRepository<User, Long> {

    public UserRepository(EntityManager em) {
        super(User.class, em);
    }
}
