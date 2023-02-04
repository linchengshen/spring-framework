package com.gitlab.springmvc.service.impl;

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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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
            for (int i = 0; i < counter; i++) {
                String key = RedisKey.TEST_PIPELINED + i;
                jedis.del(key);
            }
            Pipeline pipelined = jedis.pipelined();
            Map<String, Response<String>> responses = new LinkedHashMap<>();
            for (int i = 0; i < counter; i++) {
                String key = RedisKey.TEST_PIPELINED + i;
                Response<String> response = pipelined.set(key, String.valueOf(i));
                responses.put(key, response);
            }
            // 这行代码才发送命令到redis服务器
            pipelined.sync();
            for (Map.Entry<String, Response<String>> entry : responses.entrySet()) {
                log.info("testPipelined----key:{},value:{}", entry.getKey(), Optional.ofNullable(entry.getValue()).map(Response::get).orElse(StringUtils.EMPTY));
            }
        } finally {
            RedisOperationUtil.release(jedis);
        }
    }
}
