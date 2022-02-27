package com.gitlab.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@ComponentScan("com.gitlab")
public class AppConfig {
	public void sayHello() {
		System.out.println("111");
	}
}
