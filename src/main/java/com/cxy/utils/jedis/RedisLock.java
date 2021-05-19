package com.cxy.utils.jedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName RedisLock.java
 * @Description redisLock
 * @createTime 2021年05月19日 15:23:00
 */
@Component
@Slf4j
public class RedisLock {

//    @Autowired
//    private JedisPool jedisPool;
    private JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();

    @Resource
    public SetParams setParams;

    public static final String UNLOCK_LUA;
    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;


    //此处设置多个db分别负责单个任务，方便查看。总共0-15
    public static final Integer WX_VISIT_DB = 1;
    public static final Integer COMMON_DB = 0;
    public static final Integer LOCK_DB = 2;
    public static final Integer ORDER_CANCEL_DB = 3;


    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }



    public Boolean tryLock(String redisKey, String value, Long expire) {
        while (!lock(redisKey, value, expire)) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public Boolean lock(String redisKey, String value, Long expire) {
        Jedis jedis = null;
        Object result = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(LOCK_DB);
            result = jedis.set(redisKey, value, setParams.nx().px(expire));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    public Boolean unlock(String redisKey, String value) {
        //eval函数为执行Lua脚本
        Jedis jedis = null;
        Object result = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(LOCK_DB);
            result = jedis.eval(UNLOCK_LUA, Collections.singletonList(redisKey), Collections.singletonList(value));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}
