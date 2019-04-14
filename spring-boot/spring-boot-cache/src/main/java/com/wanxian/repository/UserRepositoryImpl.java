package com.wanxian.repository;

import com.wanxian.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> userRepositoty = new HashMap<>();

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean save(User user) {
        redisTemplate.opsForValue().set(user.getId(), user);
        return true;
//        return userRepositoty.putIfAbsent(user.getId().toString(), user) == null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public User getUser(String id) {
        return (User) redisTemplate.opsForValue().get(id);
//        return userRepositoty.get(id);
    }
}
