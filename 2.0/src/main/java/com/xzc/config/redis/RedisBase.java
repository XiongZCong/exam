package com.xzc.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@ConfigurationProperties(prefix = "redis")
public class RedisBase {
    private static String host;
    private static int port;
    private static int timeout;//秒
    private static String password;
    private static int poolMaxTotal;
    private static int poolMaxIdle;
    private static int poolMaxWait;//秒

    private static JedisPool jedisPool = null;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(int poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }

    /*
     * 初始化redis连接池
     * */
    private static void initPool() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(poolMaxTotal);//最大连接数
            config.setMaxIdle(poolMaxIdle);//最大空闲连接数
            config.setMaxWaitMillis(poolMaxWait);//获取可用连接的最大等待时间
            //jedisPool = new JedisPool(config, host, port);
            jedisPool = new JedisPool(config, host, port,
                    timeout * 1000, password, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 获取jedis实例
    * */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool == null) {
                initPool();
            }
            Jedis jedis = jedisPool.getResource();
            // jedis.auth("123456");//密码
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
