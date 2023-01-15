package com.gitlab.springmvc.util;

import com.gitlab.springmvc.common.MsgCode;
import com.gitlab.springmvc.common.Response;

public class ResponseBuilder {

    public static <T> Response<T> buildFail() {
        Response<T> response = new Response<>();
        MsgCode networkError = MsgCode.NETWORK_ERROR;
        response.setCode(networkError.getCode());
        response.setMessage(networkError.getMessage());
        response.setData(null);
        return response;
    }

    public static <T> Response<T> buildFail(String message) {
        Response<T> response = new Response<>();
        response.setCode(MsgCode.NETWORK_ERROR.getCode());
        response.setMessage(message);
        response.setData(null);
        return response;
    }

    public static <T> Response<T> buildSuccess(T data) {
        Response<T> response = new Response<>();
        response.setCode(MsgCode.SUCCESS.getCode());
        response.setMessage(MsgCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }

    public static <T> Response<T> buildSuccess() {
        Response<T> response = new Response<>();
        response.setCode(MsgCode.SUCCESS.getCode());
        response.setMessage(MsgCode.SUCCESS.getMessage());
        response.setData(null);
        return response;
    }
}
