package com.gitlab.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TransactionPropagationService {

	@Autowired
	private PersonService personService;

	@Autowired
	private CustomerService customerService;


	/**
	 * 测试事务传播,使用ThreadLocal 当前线程内处理
	 */
	@Transactional
	public void doWork() {
		personService.deleteById(13);
		customerService.update();
		int i = 1 / 0;
	}
}
