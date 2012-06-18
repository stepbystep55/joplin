package com.ippoippo.joplin.mongo.operations;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ippoippo.joplin.dto.Vote;

public class VoteHistoryOperations extends AbstractOperations {

	
	public VoteHistoryOperations(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	public void create(Vote vote) {
		mongoOperations().insert(vote, Vote.class.getSimpleName());
	}
	

	public Long countByArticleIdAndUserId(String articleId, String userId) {
		Query query = Query.query(Criteria.where("articleId").is(articleId).and("userId").is(userId));
		return mongoOperations().count(query, Vote.class.getSimpleName());
	}

	public void deleteByUserId(String userId) {
		Query query = Query.query(Criteria.where("userId").is(userId));
		mongoOperations().remove(query, Vote.class.getSimpleName());
	}
}
