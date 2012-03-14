package com.ippoippo.joplin.mongo.operations;

import org.springframework.data.mongodb.core.MongoOperations;

public abstract class AbstractOperations {


	private MongoOperations mongoOperations;

	public AbstractOperations(MongoOperations mongoOperations) {
		super();
		this.mongoOperations = mongoOperations;
	}
	
	/**
	 * get raw operations
	 * @return raw operations
	 */
	public MongoOperations mongoOperations() {
		return this.mongoOperations;
	}
}
