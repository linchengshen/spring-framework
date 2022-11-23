package com.gitlab.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoConfig {


	@Bean
	public static A aObject() {
		return new A();
	}

	@Bean
	public A  getA() {
		return new A();
	}

	public A  getXXXXX() {
		return new A();
	}
}
