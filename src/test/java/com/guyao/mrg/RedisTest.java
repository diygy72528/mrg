package com.guyao.mrg;

import com.alibaba.fastjson.JSON;
import com.guyao.mrg.config.RedisConfig;
import com.guyao.mrg.mvc.dict.entity.DictData;
import com.guyao.mrg.mvc.menu.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author guyao
 * @date 2019/11/15 3:41 下午
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class RedisTest {
    @Autowired
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
        //hash.put("dict","性别","男上加男");
        Menu menu = new Menu();
        menu.setName("张三");
        menu.setId("adfsafd");
        menu.setCreateTime(LocalDateTime.now());
        hash.put("dict","对象1", JSON.toJSONString(menu));
    }

    @Test
    public void test4() {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        Object o = hash.get("dict", "对象1");
        Menu menu = JSON.parseObject(o.toString(), Menu.class);

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
        String[] arr = {"sex","status" , "ceshi" , "1"};
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(arr));

        List dict_cache = ops.multiGet("dict_cache", strings);
        System.out.println(dict_cache);
    }

    @Test
    public void test9() throws InterruptedException {
        Long dict_cache = redisTemplate.opsForHash().size("dict_cache");
        Map dict_cache1 = redisTemplate.opsForHash().entries("dict_cache");
        System.out.println(dict_cache1);
    }

}
