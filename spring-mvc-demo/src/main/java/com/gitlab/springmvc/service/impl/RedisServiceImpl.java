package com.gitlab.springmvc.service.impl;

import com.alibaba.fastjson.JSON;
import com.gitlab.springmvc.common.RedisKey;
import com.gitlab.springmvc.service.RedisService;
import com.gitlab.springmvc.util.RedisOperationUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.*;


@Service
public class RedisServiceImpl implements RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);


    @Override
    public void testWatch() {
        Jedis jedis = RedisOperationUtil.getJedis();
        try {
            String key = RedisKey.TEST_WATCH;
            if (!jedis.exists(key)) {
                jedis.incr(key);
            }
            log.info("before incr {}={}", key, jedis.get(key));

            // watch可以执行多次
            // 事务exec时，会根据watch的可以有无发生变化这一条件，进行执行事务或丢弃事务。
            // 无论执行或丢弃，不再继续watch key 因为exec 已经兑现这个watch条件了
            jedis.watch(key);
//            jedis.watch(k1,k2)
            // 开启事务，直到exec执行前，命令在服务器中队列中
            Transaction transaction = jedis.multi();
            // 事务中的Response<?> 有点像Future，事务执行成功才有值
            Response<Long> response = transaction.incr(key);

            // 事务执行后才能调用此方法，否则报错
//            response.get();
            // 依次 返回事务中命令执行的值,如果事务由于watch的key发生改变则返回null
            List<Object> resultList = transaction.exec();
            log.info("after incr {}={}, response.get：{},是否执行事务：{}", key, jedis.get(key), response.get(), CollectionUtils.isNotEmpty(resultList));
        } finally {
            RedisOperationUtil.release(jedis);
        }
    }


    @Override
    public void testPipelined() {
        Jedis jedis = RedisOperationUtil.getJedis();
        try {
            int counter = 10;
            int ttlInSeconds = 60;
            for (int i = 0; i < counter; i++) {
                String key = RedisKey.TEST_PIPELINED + i;
                jedis.del(key);
            }
            Pipeline pipelined = jedis.pipelined();
            Map<String, Response<String>> responses = new LinkedHashMap<>();
            for (int i = 0; i < counter; i++) {
                String key = RedisKey.TEST_PIPELINED + i;
                // 将命令写入socket缓冲区，将命令写入，没有flush
                // 不等待响应，Response相当于Holder
                Response<String> response = pipelined.set(key, String.valueOf(i));
                pipelined.expire(key, ttlInSeconds);
                responses.put(key, response);
            }
            // pipeline非事务
            // 这行代码才发送命令到redis服务器
            // 并通过socket读取服务器响应
            pipelined.sync();
            for (Map.Entry<String, Response<String>> entry : responses.entrySet()) {
                log.info("testPipelined----key:{},value:{}", entry.getKey(), Optional.ofNullable(entry.getValue()).map(Response::get).orElse(StringUtils.EMPTY));
            }
        } finally {
            RedisOperationUtil.release(jedis);
        }
    }

    @Override
    public void testPipelinedSyncAndReturnAll() {
        Jedis jedis = RedisOperationUtil.getJedis();
        try {
            final int counter = 10;
            Pipeline pipelined = jedis.pipelined();
            List<String> keys = new ArrayList<>(counter);
            for (int i = 0; i < counter; i++) {
                String key = RedisKey.TEST_PIPELINED + i;
                pipelined.get(key);
                keys.add(key);
            }
            List<Object> objects = pipelined.syncAndReturnAll();
            for (int i = 0; i < counter; i++) {
                log.info("testPipelinedSyncAndReturnAll----key:{},value:{}", keys.get(i), objects.get(i));
            }
        } finally {
            RedisOperationUtil.release(jedis);
        }
    }

    @Override
    public void testTransaction() {
        Jedis jedis = RedisOperationUtil.getJedis();
        try {
            final String key = RedisKey.TEST_TRANSACTION;
            jedis.watch(key);
            log.info("before transaction----key:{},value:{}", key, jedis.get(key));
            Transaction transaction = jedis.multi();
            Response<Long> response1 = transaction.incr(key);
            Response<Long> response2 = transaction.incr(key);
            List<Object> results = transaction.exec();
            if (CollectionUtils.isNotEmpty(results)) {
                log.info("after transaction----key:{},response1:{},response2:{}, results:{}", key, response1.get(), response2.get(), JSON.toJSON(results));
            } else {
                log.error("redis事务被丢弃");
            }
        } finally {
            RedisOperationUtil.release(jedis);
        }
    }
}
