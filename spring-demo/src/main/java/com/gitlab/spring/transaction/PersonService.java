package com.gitlab.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class PersonService {

	private JdbcTemplate jdbcTemplate;


	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void update() {
		String sql = "UPDATE JPA_PERSONS set email=\"linchengshen@yeah33.net\" where id=9";
		int update = jdbcTemplate.update(sql);
		// 这里不会走到事务的拦截逻辑，认真思考一下，通过代理方法调用 进入aop拦截器链才能开启事务
		// 这里时执行目标方法内部了，调用被代理对象的方法，而不是代理对象，这种情况下不会在开启事务，update方法上的事务
		deleteById(11);
		//int i = 1/0;
	}


	@Transactional
	public void deleteById(Integer id) {
		String sql =String.format( "delete from JPA_PERSONS where id=%d", id);
		int update = jdbcTemplate.update(sql);
		System.out.println(update);
	}
}
