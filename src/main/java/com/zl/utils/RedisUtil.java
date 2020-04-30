package com.zl.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zl
 */
@Component
public class RedisUtil{

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key,Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value, long timeout) {
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

    public void addToSet(String key, Object... value) {
        redisTemplate.opsForSet().add(key,value);
    }

    public Set<Object> getFromSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public void removeFromSet(String key, Object... value) {
        redisTemplate.opsForSet().remove(key,value);
    }

}
