package com.wanxian.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching //激活
public class CacheConfiguration {
    /**
     * {@link SimpleCacheManager}
     * {@link CacheManager}
     * 定义一个CacheManager的bean 方法名即bean名称(simpleCacheManager)
     *
     * @return
     */
    @Bean
    public CacheManager simpleCacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        ConcurrentMapCache cache = new ConcurrentMapCache("cache-1");
        ConcurrentMapCache userCache = new ConcurrentMapCache("user");
        simpleCacheManager.setCaches(Arrays.asList(cache, userCache));
        return simpleCacheManager;
    }
}
