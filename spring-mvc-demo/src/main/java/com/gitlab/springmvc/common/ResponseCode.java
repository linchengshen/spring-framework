package com.gitlab.springmvc.common;

public enum ResponseCode {

    NETWORK_ERROR(100, "网络错误，请稍后再试"),

    SUCCESS(200, "");


    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
