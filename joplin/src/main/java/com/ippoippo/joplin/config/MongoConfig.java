package com.ippoippo.joplin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * MongoDB Configuration.
 */
@Configuration
public class MongoConfig {

	@Value("${mongo.host}")
	private String mongoHost;

	@Value("${mongo.port}")
	private int mongoPort;

	@Bean
	public MongoFactoryBean mongo() {
		MongoFactoryBean mongo = new MongoFactoryBean();
		mongo.setHost(mongoHost);
		mongo.setPort(mongoPort);
		return mongo;
	}

	public @Bean MongoOperations mongoOperations() throws Exception {
		return new MongoTemplate(mongo().getObject(), "mongo");
	}
}