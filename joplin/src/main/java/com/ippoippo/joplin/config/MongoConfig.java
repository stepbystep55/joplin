package com.ippoippo.joplin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.ippoippo.joplin.mongo.operations.MongoConnectionOperations;

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

	@Bean
	public MongoOperations mongoOperations() throws Exception {
	//public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo().getObject(), "joplin");
	}
	
	@Bean
	public MongoConnectionOperations mongoConnectionOperations() throws Exception {
		return new MongoConnectionOperations(mongoOperations());
	}
}