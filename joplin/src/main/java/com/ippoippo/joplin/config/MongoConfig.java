package com.ippoippo.joplin.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.ippoippo.joplin.mongo.operations.ArticleOperations;
import com.ippoippo.joplin.mongo.operations.ContributionOperations;
import com.ippoippo.joplin.mongo.operations.FriendListOperations;
import com.ippoippo.joplin.mongo.operations.UtilOperations;
import com.ippoippo.joplin.mongo.operations.VoteHistoryOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

/**
 * MongoDB Configuration.
 */
@Configuration
public class MongoConfig {

	@Value("${mongo.url}")
	private String mongoUrl;

	@Bean
	public MongoOperations mongoOperations() throws MongoException, UnknownHostException {
		MongoURI mongoURI = new MongoURI(mongoUrl);
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoURI);
		return new MongoTemplate(mongoDbFactory);
	}
	
	@Bean
	public ArticleOperations articleOperations() throws MongoException, UnknownHostException {
		return new ArticleOperations(mongoOperations());
	}
	
	@Bean
	public YoutubeItemOperations youtubeItemOperations() throws MongoException, UnknownHostException {
		return new YoutubeItemOperations(mongoOperations());
	}

	@Bean
	public ContributionOperations contributionOperations() throws MongoException, UnknownHostException {
		return new ContributionOperations(mongoOperations());
	}

	@Bean
	public VoteHistoryOperations voteHistoryOperations() throws MongoException, UnknownHostException {
		return new VoteHistoryOperations(mongoOperations());
	}

	@Bean
	public FriendListOperations friendListOperations() throws MongoException, UnknownHostException {
		return new FriendListOperations(mongoOperations());
	}

	@Bean
	public UtilOperations utilOperations() throws MongoException, UnknownHostException {
		return new UtilOperations(mongoOperations());
	}
}