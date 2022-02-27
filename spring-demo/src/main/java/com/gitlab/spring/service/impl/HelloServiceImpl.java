package com.gitlab.spring.service.impl;

import com.gitlab.spring.service.HelloService;

public class HelloServiceImpl implements HelloService {
	@Override
	public void sayHello(String name) {
		System.out.println("hello," + name);
	}
}
