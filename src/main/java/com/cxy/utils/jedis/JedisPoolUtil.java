package com.cxy.utils.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName JedisPoolUtil.java
 * @Description
 * @createTime 2021年05月19日 16:35:00
 */
public class JedisPoolUtil {

    private static volatile JedisPool jedisPool = null;// 被volatile修饰的变量不会被本地线程缓存，对该变量的读写都是直接操作共享内存。

    private JedisPoolUtil() {
    }

    public static JedisPool getJedisPoolInstance() {
        if (null == jedisPool) {
            synchronized (JedisPoolUtil.class) {
                if (null == jedisPool) {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxTotal(1000);
                    poolConfig.setMaxIdle(32);
                    poolConfig.setMaxWaitMillis(100 * 1000);
                    poolConfig.setTestOnBorrow(true);

                    jedisPool = new JedisPool(poolConfig, "192.168.10.133", 6379, 2000, "123456");
                }
            }
        }
        return jedisPool;
    }

    public static void release(JedisPool jedisPool, Jedis jedis) {
        if (null != jedis) {
            Jedis jedis2 = null;
            try {
                jedis2 = jedisPool.getResource();
            } finally {
                jedis2.close();
            }
        }
    }

}

//public class TestJedisPool {
//    public static void main(String[] args) {
//        JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
//        Jedis jedis = null;
//
//        try {
//            jedis = jedisPool.getResource();
//            jedis.set("k18", "v183");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            JedisPoolUtil.release(jedisPool, jedis);
//        }
//    }
//
//}
