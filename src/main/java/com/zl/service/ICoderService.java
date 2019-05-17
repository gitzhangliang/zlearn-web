package com.zl.service;

import com.zl.domain.Coder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Cacheable;

/**
 * @author tzxx
 * @date 2018/11/29.
 */
public interface ICoderService {
    Coder get(long id);

    Coder getForKeyGenerator(long id);

    Coder find(long id);

    void saveToRedisByRedisTemplate(long id);

    @Transactional(rollbackFor = RuntimeException.class)
    void save(Coder coder);

    @Transactional(rollbackFor = RuntimeException.class)
    void saveCoder(Coder coder);
}
