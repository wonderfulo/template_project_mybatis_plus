//package com.cxy.util;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RedisCacheUtil {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//    @Autowired
//    private ObjectMapper mapper;
//
//    private String lock_key = "redis_lock"; //锁键
//
//    protected long internalLockLeaseTime = 30000;//锁过期时间
//
//    private long timeout = 999999; //获取锁的超时时间
//
//
//    public static cn.supstore.core.base.cache.RedisCacheUtil getObject() {
//        return AppUtil.getCtx().getBean(cn.supstore.core.base.cache.RedisCacheUtil.class);
//    }
//
//    public void set(final String key, final Object value, final long expiredTime) {
//        BoundValueOperations<String, Object> valueOper = redisTemplate.boundValueOps(key);
//        // String valueStr = JSONObject.fromObject(value).toString();
//        String valueStr = "";
//        try {
//            valueStr = mapper.writeValueAsString(value);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        if (expiredTime <= 0) {
//            valueOper.set(valueStr);
//        } else {
//            valueOper.set(valueStr, expiredTime, TimeUnit.MILLISECONDS);
//        }
//    }
//
//    public void set(final String key, final String value, final long expiredTime) {
//        BoundValueOperations<String, Object> valueOper = redisTemplate.boundValueOps(key);
//        // String valueStr = JSONObject.fromObject(value).toString();
//        if (expiredTime <= 0) {
//            valueOper.set(value);
//        } else {
//            valueOper.set(value, expiredTime, TimeUnit.MILLISECONDS);
//        }
//    }
//
//    public Object get(final String key) {
//        BoundValueOperations<String, Object> valueOper = redisTemplate.boundValueOps(key);
//        return valueOper.get();
//    }
//
//    public Object get(final String key, final long expiredTime) {
//        BoundValueOperations<String, Object> valueOper = redisTemplate.boundValueOps(key);
//        Object obj = valueOper.get();
//        if (obj != null) {
//            valueOper.expire(expiredTime, TimeUnit.MILLISECONDS);
//        }
//        return obj;
//    }
//
//    public void del(String key) {
//        if (redisTemplate.hasKey(key)) {
//            redisTemplate.delete(key);
//        }
//    }
//
//    public Boolean check(String key, String value) {
//        Boolean flag = false;
//        if (redisTemplate.hasKey(key) && value.equals(get(key))) {
//            flag = true;
//        }
//        return flag;
//    }
//
//    public <T> T getGenericObject(String json, Class<T> clz) {
//        T t = null;
//        try {
//            t = mapper.readValue(json, clz);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return t;
//    }
//
//    public <T> List<T> getGenericArray(String json, Class<T> clz) {
//        List<T> list = null;
//        try {
//            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clz);
//            list = (List<T>) mapper.readValue(json, javaType);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    /**
//     * 通过token获取泛型对象信息
//     * @param token
//     * @param clz
//     * @return
//     */
//    /*public <T> T getGenericObject(String token, Class<T> clz) {
//    	String json = CoverUtil.Obj2str(get(token));
//    	T t = null;
//    	try {
//			t = mapper.readValue(json, clz);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	return t;
//    }
//
//    *//**
//     * 通过token获取泛型对象信息并更新存储时间
//     * @param token
//     * @param clz
//     * @param expiredTime 存储时间
//     * @return
//     *//*
//    public <T> T getGenericObject(String token, Class<T> clz, long expiredTime) {
//    	String json = CoverUtil.Obj2str(get(token, expiredTime));
//    	T t = null;
//    	try {
//			t = mapper.readValue(json, clz);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	return t;
//    }*/
//
//
//    /**
//     * 加锁
//     *
//     * @param key
//     * @return
//     */
//    public boolean lock(String key) {
//        BoundValueOperations<String, Object> valueOper = redisTemplate.boundValueOps(key);
//        Long start = System.currentTimeMillis();
//        try {
//            long l = System.currentTimeMillis() - start;
//            // 未超时
//            while (l < timeout) {
//                //SET命令返回OK ，则证明获取锁成功
//                Boolean succeed = valueOper.setIfAbsent(key);
//                valueOper.expire(internalLockLeaseTime,TimeUnit.MILLISECONDS);
//                Thread.sleep(100);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//}
