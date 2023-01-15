package com.gitlab.springmvc.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Properties;

@Component
public class JedisConfig {

	private static final String JEDIS_PROPERTIES_FILE = "jedis.properties";

	private static final Properties jedisProperties;

	static {
		try {
			jedisProperties = PropertiesLoaderUtils.loadAllProperties(JEDIS_PROPERTIES_FILE);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	JedisPool jedisPool() {
		GenericObjectPoolConfig<Jedis> jedisGenericPoolConfig = new GenericObjectPoolConfig<>();
		jedisGenericPoolConfig.setMaxTotal(20);
		jedisGenericPoolConfig.setMaxIdle(10);
		jedisGenericPoolConfig.setMinIdle(5);

		return new JedisPool(jedisGenericPoolConfig,
				jedisProperties.getProperty("redis.host"),
				Integer.parseInt(jedisProperties.getProperty("redis.port"))
		);
	}
}
