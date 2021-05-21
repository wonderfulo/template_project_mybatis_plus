package com.cxy.redis;

import com.cxy.mapper.SysUserMapper;
import com.cxy.utils.jedis.JedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName RedisTest.java
 * @Description redis测试
 * @createTime 2021年05月21日 15:04:00
 */
//该注解会启动springboot项目
@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void jedisTest() {
        JedisUtil jedisUtil = new JedisUtil();
        Boolean age = jedisUtil.set("age", "12", 1);
        String age1 = jedisUtil.get("age", 1);
        System.out.println(age1);
    }


    @Test
    public void redisTemplateTest() {
        //1、通过redisTemplate设置值
        redisTemplate.boundValueOps("StringKey").set("StringValue");
        Object stringKey1 = redisTemplate.boundValueOps("StringKey").get();

//        redisTemplate.boundValueOps("StringKey").set("StringValue", 1, TimeUnit.MINUTES);
//
//        //2、通过BoundValueOperations设置值
//        BoundValueOperations stringKey = redisTemplate.boundValueOps("StringKey");
//        stringKey.set("StringVaule");
//        stringKey.set("StringValue", 1, TimeUnit.MINUTES);
//
//        //3、通过ValueOperations设置值
//        ValueOperations ops = redisTemplate.opsForValue();
//        ops.set("StringKey", "StringVaule");
//        ops.set("StringValue", "StringVaule", 1, TimeUnit.MINUTES);

    }

    //    删除key
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    //    删除多个key
    public void deleteKey(String... keys) {
        redisTemplate.delete(keys);
    }

    //    指定key的失效时间
    public void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.MINUTES);
    }

    //    根据key获取过期时间
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key);
        return expire;
    }

    //    判断key是否存在
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

}
