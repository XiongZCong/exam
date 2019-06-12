package com.xzc.config.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisUtil {

    private static String className = "";
    public static final String Field_ID = "id";
    public static final String Field_NAME = "name";
    public static final String VerificationCode = "code";
    public static final String Type_ArrayList = "ArrayList";

    private static String getPrefix(String className, String field, String key) {
        return className + ":" + field + ":" + key;
    }

    /**
     * 获取单个对象
     */
    public static <T> T get(Class<T> clazz, String field, String key) {
        Jedis jedis = RedisBase.getJedis();
        className = clazz.getSimpleName();
        String realKey = getPrefix(className, field, key);
        String str = jedis.get(realKey);
        T t = JSON.parseObject(str, clazz);
        jedis.close();
        return t;
    }

    public static <T> List getList(String type, String field, String key, Class<T> clazz) {
        Jedis jedis = RedisBase.getJedis();
        String realKey = getPrefix(type, field, key);
        String str = jedis.get(realKey);
        List list = JSONArray.parseArray(str, clazz);
        jedis.close();
        return list;
    }

    /**
     * 设置对象
     */
    public static <T> boolean set(T obj, String field, String key) {
        return set(obj, field, key, 0);
    }

    public static <T> boolean set(T obj, String field, String key, int expire) {
        Jedis jedis = RedisBase.getJedis();
        className = obj.getClass().getSimpleName();
        String str = JSON.toJSONString(obj);
        if (str == null || str.length() <= 0) {
            jedis.close();
            return false;
        }
        String realKey = getPrefix(className, field, key);
        if (expire <= 0) {
            jedis.set(realKey, str);
        } else {
            jedis.setex(realKey, expire, str);
        }
        jedis.close();
        return true;
    }

    /**
     * 判断key是否存在
     */
    public static <T> boolean exists(Class<T> clazz, String field, String key) {
        Jedis jedis = RedisBase.getJedis();
        className = clazz.getSimpleName();
        String realKey = getPrefix(className, field, key);
        boolean result = jedis.exists(realKey);
        jedis.close();
        return result;
    }

    /**
     * 增加值
     */
    public static <T> Long incr(Class<T> clazz, String field, String key) {
        Jedis jedis = RedisBase.getJedis();
        className = clazz.getSimpleName();
        String realKey = getPrefix(className, field, key);
        Long result = jedis.incr(realKey);
        return result;
    }

    /**
     * 减少值
     */
    public static <T> Long decr(Class<T> clazz, String field, String key) {
        Jedis jedis = RedisBase.getJedis();
        className = clazz.getSimpleName();
        String realKey = getPrefix(className, field, key);
        Long result = jedis.decr(realKey);
        jedis.close();
        return result;
    }
}
