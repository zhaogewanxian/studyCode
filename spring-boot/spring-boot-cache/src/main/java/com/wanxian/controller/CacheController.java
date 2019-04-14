package com.wanxian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CacheController {
    @Autowired
    @Qualifier("simpleCacheManager")
    private CacheManager cacheManager;

    @PostMapping("save")
    public Map<String, Object> save(String key, String value) {
        Map<String, Object> resultMap = new HashMap<>();
        Cache cache = cacheManager.getCache("cache-1");
        cache.put(key, value);
        return resultMap;
    }

}
