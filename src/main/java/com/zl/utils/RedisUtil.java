package com.zl.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author tzxx
 */
@Component
public class RedisUtil<V>{

    @Resource
    private RedisTemplate<String, V> redisTemplate;

    public void set(String key,V value) {
        redisTemplate.opsForValue().set(key,value);
    }

    public V get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, V value, long timeout) {
        redisTemplate.opsForValue().set(key,value,timeout, TimeUnit.SECONDS);
    }

    public void remove(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    public boolean exists(String key) {
        if(key != null){
            return redisTemplate.hasKey(key);
        }else {
            return false;
        }
    }

    public void addToSet(String key, V... value) {
        redisTemplate.opsForSet().add(key,value);
    }

    public Set<V> getFromSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public void removeFromSet(String key, V... value) {
        redisTemplate.opsForSet().remove(key,value);
    }

}
