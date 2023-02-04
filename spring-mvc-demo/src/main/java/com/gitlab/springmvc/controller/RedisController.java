package com.gitlab.springmvc.controller;

import com.gitlab.springmvc.common.RedisKey;
import com.gitlab.springmvc.common.Response;
import com.gitlab.springmvc.dto.RedisValueDTO;
import com.gitlab.springmvc.service.RedisService;
import com.gitlab.springmvc.util.RedisOperationUtil;
import com.gitlab.springmvc.util.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@RestController
@RequestMapping("/redis")
@Validated
public class RedisController {

    @Autowired
    private RedisService redisService;


    @GetMapping("/set")
    public Response<String> set(@Valid RedisValueDTO redisValueDTO) {
        String key = redisValueDTO.getKey();
        String value = redisValueDTO.getValue();
        Long secondsToExpired = redisValueDTO.getSecondsToExpired();
        if (Objects.nonNull(secondsToExpired)) {
            RedisOperationUtil.set(key, value, secondsToExpired);
        } else {
            RedisOperationUtil.set(key, value);
        }
        return Responses.success("");
    }

    @GetMapping("/get")
    public Response<String> get(@RequestParam String key) {
        return Responses.success(RedisOperationUtil.get(key));
    }

    @GetMapping("/addList")
    public Response<String> addList(@RequestParam String value) {
        RedisOperationUtil.setList(RedisKey.LIST, value);
        return Responses.success();
    }

    @GetMapping("/index")
    public Response<String> index(@NotBlank @RequestParam String value) {
        return Responses.success();
    }


    @GetMapping("/test/watch")
    public Response<String> testWatch() {
        redisService.testWatch();
        return Responses.success();
    }

    @GetMapping("/test/pipelined")
    public Response<String> testPipelined() {
        redisService.testPipelined();
        return Responses.success();
    }
}
