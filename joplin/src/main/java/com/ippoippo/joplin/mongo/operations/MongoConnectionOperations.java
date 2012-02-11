package com.ippoippo.joplin.mongo.operations;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.ippoippo.joplin.dto.UsersConnection;

public class MongoConnectionOperations {

	private MongoOperations mongoOperations;

	public MongoConnectionOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
	
	/**
	 * get raw operations
	 * @return raw operations
	 */
	public MongoOperations raw() {
		return this.mongoOperations;
	}

	public List<UsersConnection> listByUserIdAndProviderId(String userId, String providerId) {
		Query query = Query.query(Criteria.where("userId").is(userId).and("providerId").is(providerId));
		return mongoOperations.find(query, UsersConnection.class);
	}
	
	public Integer getMaxRankByUserIdAndProviderId(String userId, String providerId) {
		Query query = Query.query(Criteria.where("userId").is(userId).and("providerId").is(providerId));
		query.sort().on("rank", Order.DESCENDING);
		query.limit(1);
		UsersConnection usersConnection = mongoOperations.findOne(query, UsersConnection.class);
		if (usersConnection == null) return 1;
		return usersConnection.getRank();
	}
}
