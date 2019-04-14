package com.wanxian.repository;

import com.wanxian.entity.User;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> userRepositoty = new HashMap<>();

    @Override
    public boolean save(User user) {
        return userRepositoty.putIfAbsent(user.getId().toString(), user) == null;
    }


    /**

     *
     * @param id
     * @return
     */
    @Override
    public User getUser(String id) {
        return userRepositoty.get(id);
    }
}
