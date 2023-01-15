package com.gitlab.springmvc.config;


import com.gitlab.springmvc.common.Response;
import com.gitlab.springmvc.util.ResponseBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.exceptions.JedisDataException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandler {

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Response<String> handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        return ResponseBuilder.buildFail();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(JedisDataException.class)
    public Response<String> handleException(JedisDataException e) {
        e.printStackTrace();
        return ResponseBuilder.buildFail();
    }
}
