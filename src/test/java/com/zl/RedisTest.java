package com.zl;

import com.zl.cache.CacheService;
import com.zl.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
    @Resource
    private CacheService cacheService;
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

    @Test
    public void test2(){
//        cacheService.zSet("l","java",1D);
//        cacheService.zSet("l","c",2D);
//        cacheService.zSet("l","c++",3D);
        cacheService.zSet("l","php",5D);
//        System.out.println(cacheService.zSetContains("l", "java"));
//        cacheService.zSetList("l").forEach(System.out::println);
//        System.out.println(cacheService.zSetCount("l"));
//        cacheService.zSetRemove("l","java");
//        System.out.println(cacheService.zSetContains("l", "java"));

    }
}
