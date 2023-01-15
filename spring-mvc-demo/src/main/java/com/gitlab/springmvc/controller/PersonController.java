package com.gitlab.springmvc.controller;

import com.gitlab.springmvc.common.Response;
import com.gitlab.springmvc.dto.PersonFormDTO;
import com.gitlab.springmvc.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/save")
    public Response<String> savePerson(@Valid PersonFormDTO personFormDTO) {
        Jedis jedis = jedisPool.getResource();
        String key = "demo-key";
        String value;
        try {
            if (jedis.exists(key)) {
                value = jedis.get(key);
            } else {
                value = "world";
                jedis.set(key, value);
            }
        } finally {
            jedis.close();
        }
        return ResponseBuilder.buildSuccess(value);
    }
}
