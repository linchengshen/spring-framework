package com.gitlab.springmvc.controller;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/http-entity")
public class DemoHttpEntityController {

	@RequestMapping("/index")
	public HttpEntity<Map<String, Object>> index() {
		Map<String, Object> map = new HashMap<>();
		map.put("hello", "world");
		return new HttpEntity<>(map);
	}

	@RequestMapping("/getPerson")
	@ResponseBody
	public Map<String, Object> getPerson(@Valid @NotBlank String id) {
		Map<String, Object> data = new HashMap<>();
		data.put("userName","linchengshen");
		data.put("age", "20");
		data.put("id", id);
		return data;
	}


}
