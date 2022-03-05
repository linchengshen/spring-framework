package com.gitlab.spring.transaction;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TransactionConfig.class);
		PersonService personService = context.getBean(PersonService.class);
		personService.update();
	}
}
