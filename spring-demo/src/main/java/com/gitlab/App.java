package com.gitlab;

import com.gitlab.spring.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		AppConfig bean = context.getBean(AppConfig.class);
		bean.sayHello();
	}
}