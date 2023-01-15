package com.gitlab.springmvc.util;

import com.gitlab.springmvc.common.RedisKey;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

public class RedisListConsumer {

    private static final int DEFAULT_TIMEOUT = 30;

    public void start() {
        new Thread(() -> {
            System.out.println(111);
            while (true) {
                System.out.println(222);
                try {
                    List<String> list = RedisManager.brpop(RedisKey.LIST, DEFAULT_TIMEOUT);
                    if (!CollectionUtils.isEmpty(list)) {
                        System.out.println(Arrays.toString(list.toArray(new String[0])));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "list-consumer-thread").start();
    }
}
