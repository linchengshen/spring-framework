package com.gitlab.springmvc.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Objects;

public class RedisManager {

    private volatile static JedisPool jedisPool;


    public static String get(String key) {
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.get(key);
        } finally {
            release(jedis);
        }
    }

    public static String set(String key, String value) {
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.set(key, value);
        } finally {
            release(jedis);
        }
    }

    public static String set(String key, String value, long secondsToExpire) {
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.set(key, value, SetParams.setParams().ex(secondsToExpire));
        } finally {
            release(jedis);
        }
    }


    private static JedisPool getJedisPool() {
        if (jedisPool == null) {
            synchronized (RedisManager.class) {
                if (jedisPool == null) {
                    jedisPool = Context.getBeanOfType(JedisPool.class);
                }
            }
        }
        return jedisPool;
    }

    private static void release(Jedis jedis) {
        if (Objects.nonNull(jedis)) {
            jedis.close();
        }
    }
}
