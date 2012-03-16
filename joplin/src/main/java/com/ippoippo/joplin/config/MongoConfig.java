package com.ippoippo.joplin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.ippoippo.joplin.mongo.operations.ArticleOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;
import com.mongodb.MongoURI;

/**
 * MongoDB Configuration.
 */
@Configuration
public class MongoConfig {

	@Value("${mongo.host}")
	private String mongoHost;

	@Value("${mongo.port}")
	private int mongoPort;
/*
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
*/

	@Bean
	public MongoOperations mongoOperations() throws Exception {
		//MongoURI mongoURI = new MongoURI("mongodb://joplin:summertime@ds031587.mongolab.com:31587/mongogo");
		MongoURI mongoURI = new MongoURI("mongodb://localhost:27017/joplin");
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoURI);
		return new MongoTemplate(mongoDbFactory);
	}
	
	@Bean
	public ArticleOperations articleOperations() throws Exception {
		return new ArticleOperations(mongoOperations());
	}
	
	@Bean
	public YoutubeItemOperations youtubeItemOperations() throws Exception {
		return new YoutubeItemOperations(mongoOperations());
	}
}