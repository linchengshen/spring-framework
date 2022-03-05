package com.gitlab.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomerService {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Transactional
	public int update() {
		String sql = "UPDATE JPA_CUSTOMERS set email=\"linchengshen@yeah111.net\" where id=8";
		int update = jdbcTemplate.update(sql);
		return update;
	}
}
