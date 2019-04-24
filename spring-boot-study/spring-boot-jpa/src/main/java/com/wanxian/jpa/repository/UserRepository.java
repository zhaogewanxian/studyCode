package com.wanxian.jpa.repository;

import com.wanxian.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * spring jpa
 * @see SimpleJpaRepository
 * @see JpaRepository
 * @see Repository
 */
@Repository
public class UserRepository extends SimpleJpaRepository<User, Long> {

    public UserRepository(EntityManager em) {
        super(User.class, em);
    }
}
