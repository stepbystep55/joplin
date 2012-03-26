package com.ippoippo.joplin.mongo.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ippoippo.joplin.dto.Contribution;

public class ContributionOperations extends AbstractOperations {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ContributionOperations(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	public Long countByArticleIdAndUserId(String articleId, String userId) {
		Query query = Query.query(Criteria.where("articleId").is(articleId).and("userId").is(userId));
		return mongoOperations().count(query, Contribution.class.getSimpleName());
	}

	public Contribution getByArticleIdAndUserId(String articleId, String userId) {
		Query query = Query.query(Criteria.where("articleId").is(articleId).and("userId").is(userId));
		return mongoOperations().findOne(query, Contribution.class, Contribution.class.getSimpleName());
	}

	public void create(Contribution contribution) {
		mongoOperations().insert(contribution, Contribution.class.getSimpleName());
	}
}
