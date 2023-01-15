package com.gitlab.springmvc.controller;

import com.gitlab.springmvc.common.Response;
import com.gitlab.springmvc.dto.RedisValueDTO;
import com.gitlab.springmvc.util.RedisManager;
import com.gitlab.springmvc.util.ResponseBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/redis")
public class RedisController {


    @GetMapping("/set")
    public Response<String> set(@Valid RedisValueDTO redisValueDTO) {
        String key = redisValueDTO.getKey();
        String value = redisValueDTO.getValue();
        Long secondsToExpired = redisValueDTO.getSecondsToExpired();
        if (Objects.nonNull(secondsToExpired)) {
            RedisManager.set(key, value, secondsToExpired);
        } else {
            RedisManager.set(key, value);
        }
        return ResponseBuilder.buildSuccess("");
    }

    @GetMapping("/get")
    public Response<String> get(@RequestParam String key) {
        return ResponseBuilder.buildSuccess(RedisManager.get(key));
    }
}
