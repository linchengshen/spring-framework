package com.gitlab.springmvc.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;


@Data
@ToString
public class PersonFormDTO {

	@NotBlank(message = "姓名不能为空")
	private String name;

	private Integer age;
}
