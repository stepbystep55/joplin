package com.ippoippo.joplin.mongo.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ippoippo.joplin.dto.Friends;

public class FriendListOperations extends AbstractOperations {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public FriendListOperations(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	public Friends getByProviderIdAndUserId(String providerId, String userId) {
		Query query = Query.query(Criteria.where("providerId").is(providerId).and("userId").is(userId));
		return mongoOperations().findOne(query, Friends.class, Friends.class.getSimpleName());
	}

	public void create(Friends friendList) {
		mongoOperations().insert(friendList, Friends.class.getSimpleName());
	}

	public void delete(String id) {
		Query query = Query.query(Criteria.where("id").is(id));
		mongoOperations().remove(query, Friends.class.getSimpleName());
	}
}
