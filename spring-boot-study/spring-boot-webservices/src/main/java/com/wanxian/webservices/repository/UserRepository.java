package com.wanxian.webservices.repository;

import com.wanxian.webservices.domain.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户仓储
 */
@Repository
public class UserRepository {

    private Map<Long, User> cacheUsers = new HashMap<>();

    @PostConstruct
    public void init() {
        User user1 = createUser(1, "wanxian", 24);
        User user2 = createUser(2, "wanxian", 24);
        User user3 = createUser(3, "wanxian", 24);
        cacheUsers.put(1L, user1);
        cacheUsers.put(2L, user2);
        cacheUsers.put(3L, user3);
    }

    public User findUserById(long id) {//传int进来是取不到
        return cacheUsers.get(id);
    }

    public User createUser(long id, String name, Integer age) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        return user;
    }


}
