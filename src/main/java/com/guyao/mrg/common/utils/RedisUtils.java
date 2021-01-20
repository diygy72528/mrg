package com.guyao.mrg.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author guyao
 * @date 2020/5/11 11:10 上午
 */
@Component
public class RedisUtils {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    public void pushHash(String key, String hashKey, Map map) {
        template.opsForHash().put(key,hashKey,map);
    }

    public Object gethashValue(String key, Object hashKey) {
        return template.opsForHash().get(key,hashKey);
    }

    public void checkAndRemoveHash(String key) {
        Long size = template.opsForHash().size(key);
        if(size>0) {
            template.delete(key);
        }
    }

    public void deleteHash(String key, Object hashKey) {
        template.opsForHash().delete(key,hashKey);
    }

}
