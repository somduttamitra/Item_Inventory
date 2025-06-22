package com.ecommerce.demo.Util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    private StringRedisTemplate redisTemplate;

    public CacheServiceImpl(StringRedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
