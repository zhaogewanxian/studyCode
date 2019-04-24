package com.wanxian.repository;

import com.wanxian.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository {
    /**
     * @param id
     * {@link CacheInterceptor}
     * 标记之后 类似aop拦截
     */
    @Cacheable(cacheNames = "user")
    User getUser(String id);

    boolean save(User user);
}
