package com.gitlab.springmvc.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.List;
import java.util.Objects;

public class RedisOperationUtil {

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

    public static long setList(String key, String value) {
        JedisPool jedisPool = getJedisPool();
        Jedis redis = jedisPool.getResource();
        try {
            return redis.lpush(key, value);
        } finally {
            release(redis);
        }
    }

    public static List<String> brpop(String key, int timeout) {
        JedisPool jedisPool = getJedisPool();
        Jedis redis = jedisPool.getResource();
        try {
            return redis.brpop(timeout, key);
        } finally {
            release(redis);
        }
    }

    public static String watch(Jedis jedis, String... key) {
        return jedis.watch(key);
    }


    private static JedisPool getJedisPool() {
        if (jedisPool == null) {
            synchronized (RedisOperationUtil.class) {
                if (jedisPool == null) {
                    jedisPool = Context.getBeanOfType(JedisPool.class);
                }
            }
        }
        return jedisPool;
    }

    public static void release(Jedis jedis) {
        if (Objects.nonNull(jedis)) {
            jedis.close();
        }
    }

    public static Jedis getJedis() {
        return getJedisPool().getResource();
    }
}
