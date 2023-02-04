package com.gitlab.springmvc.service;

public interface RedisService {

    /**
     * 测试redis watch、multi、exec等事务命令
     */
    void testWatch();

    /**
     * 测试 redis pipelined
     */
    void testPipelined();
}
