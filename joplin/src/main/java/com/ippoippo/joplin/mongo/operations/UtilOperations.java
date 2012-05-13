package com.ippoippo.joplin.mongo.operations;

import java.util.Set;

import org.springframework.data.mongodb.core.MongoOperations;

public class UtilOperations extends AbstractOperations {

	
	public UtilOperations(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	public Set<String> getCollectionNames() {
		return mongoOperations().getCollectionNames();
	}
}
