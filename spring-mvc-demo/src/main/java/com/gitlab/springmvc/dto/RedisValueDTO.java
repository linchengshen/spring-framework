package com.gitlab.springmvc.dto;


import javax.validation.constraints.NotBlank;

public class RedisValueDTO {

    @NotBlank(message = "key不能为空")
    private String key;

    @NotBlank(message = "value不能为空")
    private String value;

    private Long secondsToExpired;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getSecondsToExpired() {
        return secondsToExpired;
    }

    public void setSecondsToExpired(Long secondsToExpired) {
        this.secondsToExpired = secondsToExpired;
    }
}
