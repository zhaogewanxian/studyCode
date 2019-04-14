package com.wanxian;

import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
        return simpleCacheManager;
    }
}
