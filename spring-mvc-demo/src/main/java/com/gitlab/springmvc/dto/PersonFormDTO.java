package com.gitlab.springmvc.dto;

import org.hibernate.validator.constraints.NotBlank;


public class PersonFormDTO {

    @NotBlank(message = "姓名不能为空")
    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
