package com.gitlab.springmvc.util;

import com.gitlab.springmvc.common.MsgCode;
import com.gitlab.springmvc.common.Response;

public class Responses {

    public static <T> Response<T> fail() {
        return fail(null);
    }

    public static <T> Response<T> fail(String message) {
        Response<T> response = new Response<>();
        response.setCode(MsgCode.NETWORK_ERROR.getCode());
        response.setMessage(message);
        response.setData(null);
        return response;
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode(MsgCode.SUCCESS.getCode());
        response.setMessage(MsgCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }

    public static <T> Response<T> success() {
        return success(null);
    }
}
