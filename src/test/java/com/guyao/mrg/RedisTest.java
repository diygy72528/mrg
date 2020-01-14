package com.guyao.mrg;

import com.guyao.mrg.mvc.config.RedisConfig;
import com.guyao.mrg.mvc.dict.entity.DictData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author guyao
 * @date 2019/11/15 3:41 下午
 */
/*@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RedisConfig.class)*/
public class RedisTest {
    /*@Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        stringRedisTemplate.opsForList().leftPush("aa","aavalue");
    }

    @Test
    public void test1() {
        System.out.println(stringRedisTemplate.opsForValue().get("aa"));
    }

    @Test
    public void test2() {
        stringRedisTemplate.opsForValue().set("aa","value of aa");
    }

    @Test
    public void test3() {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        hash.put("dict","性别","男上加男");
    }

    @Test
    public void test4() {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        Object o = hash.get("dict", "性别");
        System.out.println(o);
    }

    @Test
    public void test5() {
        SetOperations<String,Object> ops = redisTemplate.opsForSet();
        DictData data = new DictData();
        data.setDictName("性别2");
        data.setDictTypeId("1233211234567");
        Long dict = ops.add("dict", data);
        System.out.println(dict);
    }

    @Test
    public void test6() {
        SetOperations ops = redisTemplate.opsForSet();
        Object dict = ops.members("dict");
        System.out.println(dict);
    }

    @Test
    public void test7() {
        HashOperations hash = redisTemplate.opsForHash();
        hash.put("hash","a","value of a");
    }

    @Test
    public void test8() {
        HashOperations ops = redisTemplate.opsForHash();
        Object o = ops.get("hash", "a");
        System.out.println(o);
        Map hash1 = ops.entries("hash");
        System.out.println(hash1);
    }*/

}
