package com.zl;

import com.zl.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @author tzxx
 * @date 2019/2/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void test(){
        // 保存字符串
        redisTemplate.opsForSet().add("set","1","2");
    }
    @Test
    public void test1(){
        // 保存字符串
        Set<Object> s = redisTemplate.opsForSet().members("set");
        s.forEach(System.out::println);
        redisTemplate.opsForSet().add("set","3","2");

    }
}
