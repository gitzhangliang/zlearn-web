package com.zl.service.impl;

import com.zl.domain.Coder;
import com.zl.repository.CoderRepository;
import com.zl.service.ICoderService;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author tzxx
 * @date 2018/11/29.
 */
@Service
public class CoderServiceImpl implements ICoderService {

    @Resource
    private CoderRepository coderRepository;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    //@Cacheable(value = "codes",key = "'id_' + #id")
    @Override
    public Coder get(long id) {
        System.out.println("load for db");
        return findById(id);
    }

    @Cacheable(value = "cache1")
    @Override
    public Coder getForKeyGenerator(long id) {
        System.out.println("load for db");
        return findById(id);
    }

    @Override
    public Coder find(long id) {
        Coder coder = ( Coder ) redisTemplate.opsForValue().get("coder:id:"+id);
        if(coder == null){
            saveToRedisByRedisTemplate(id);
            return ( Coder ) redisTemplate.opsForValue().get("coder:id:"+id);
        }
        return coder;
    }

    @Override
    public void saveToRedisByRedisTemplate(long id) {
        Coder coder = findById(id);
        redisTemplate.opsForValue().set("coder:id:"+id,coder);

    }

    private Coder findById(long id){
        return coderRepository.findById(id).orElseThrow(()->new RuntimeException("对象不存在"));
    }

    @Override
    public void save(Coder coder){
        saveCoder(coder);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void saveCoder(Coder coder){
        coderRepository.save(coder);
    }
}
