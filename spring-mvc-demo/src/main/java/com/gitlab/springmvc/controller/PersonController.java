package com.gitlab.springmvc.controller;

import com.gitlab.springmvc.dto.PersonFormDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

//	@Autowired
//	private JedisPool jedisPool;

	@RequestMapping("/save")
	public String savePerson(@Valid PersonFormDTO personFormDTO) {
//		Jedis jedis = jedisPool.getResource();
//		String key = "hello";
//		String value;
//		try {
//			if (jedis.exists(key)) {
//				value = jedis.get(key);
//			} else {
//				value = "world";
//				jedis.set(key, value);
//			}
//		} finally {
//			jedis.close();
//		}
//		System.out.println(key + "==" + value);
		return "success";
	}
}
