package com.zl.cache;

import java.util.List;
import java.util.Set;

/**
 * @author tzxx
 */
public interface CacheService {

    boolean containsKey(String key);

    void add(String key, Object value);

    boolean zSet(String key, Object value, Double score);

    List<Object> zSetList(String key);

    Long zSetCount(String key);

    Long zSetRemove(String key, Object value);

    boolean zSetContains(String key, Object value);
}
