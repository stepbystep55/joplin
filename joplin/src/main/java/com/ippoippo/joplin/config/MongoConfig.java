package com.ippoippo.joplin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.ippoippo.joplin.mongo.operations.ArticleOperations;
import com.ippoippo.joplin.mongo.operations.ContributionOperations;
import com.ippoippo.joplin.mongo.operations.VoteHistoryOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;
import com.mongodb.MongoURI;

/**
 * MongoDB Configuration.
 */
@Configuration
public class MongoConfig {

	@Value("${mongo.url}")
	private String mongoUrl;

	@Bean
	public MongoOperations mongoOperations() throws Exception {
		MongoURI mongoURI = new MongoURI(mongoUrl);
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

	@Bean
	public ContributionOperations contributionOperations() throws Exception {
		return new ContributionOperations(mongoOperations());
	}

	@Bean
	public VoteHistoryOperations voteHistoryOperations() throws Exception {
		return new VoteHistoryOperations(mongoOperations());
	}
}