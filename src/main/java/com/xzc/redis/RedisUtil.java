package com.xzc.redis;

import com.xzc.util.SBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisUtil {

    @Autowired
    JedisPool jedisPool;

    private String className = "";

    public static String Field_id = "id";
    public static String Field_name = "name";

    public String getPrefix(String className, String field, String key) {
        return className + "_" + field + "_" + key;
    }

    /**
     * 获取单个对象
     */
    public <T> T get(Class<T> clazz, String field, String key) {
        Jedis jedis = null;
        className = clazz.getSimpleName();
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = getPrefix(className, field, key);
            String str = jedis.get(realKey);
            T t = SBUtil.stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     */
    public <T> boolean set(T obj, String field, String key) {
        return set(obj, field, key, 0);
    }

    public <T> boolean set(T obj, String field, String key, int expire) {
        Jedis jedis = null;
        className = obj.getClass().getSimpleName();
        try {
            jedis = jedisPool.getResource();
            String str = SBUtil.beanToString(obj);
            if (str == null || str.length() <= 0) {
                return false;
            }
            String realKey = getPrefix(className, field, key);
            if (expire <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, expire, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     */
    public <T> boolean exists(Class<T> clazz, String field, String key) {
        Jedis jedis = null;
        className = clazz.getSimpleName();
        try {
            jedis = jedisPool.getResource();
            String realKey = getPrefix(className, field, key);
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     */
    public <T> Long incr(Class<T> clazz, String field, String key) {
        Jedis jedis = null;
        className = clazz.getSimpleName();
        try {
            jedis = jedisPool.getResource();
            String realKey = getPrefix(className, field, key);
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     */
    public <T> Long decr(Class<T> clazz, String field, String key) {
        Jedis jedis = null;
        className = clazz.getSimpleName();
        try {
            jedis = jedisPool.getResource();
            String realKey = getPrefix(className, field, key);
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
