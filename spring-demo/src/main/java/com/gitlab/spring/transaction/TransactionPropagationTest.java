package com.gitlab.spring.transaction;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionPropagationTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TransactionConfig.class);
		TransactionPropagationService propagationService = context.getBean(TransactionPropagationService.class);
		propagationService.doWork();
	}
}
