package com.gitlab.springmvc.config;


import com.gitlab.springmvc.common.Response;
import com.gitlab.springmvc.util.Responses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.exceptions.JedisDataException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler {

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Response<String> handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        return Responses.fail();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(JedisDataException.class)
    public Response<String> handleException(JedisDataException e) {
        e.printStackTrace();
        return Responses.fail();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
    public Response<String> handleException(HttpServletRequest request, HttpServletResponse response, BindException e) {
        List<ObjectError> allErrors = e.getAllErrors();
        String message = allErrors.stream()
                .findFirst().map(ObjectError::getDefaultMessage)
                .orElse(StringUtils.EMPTY);
        return Responses.fail(message);
    }
}
