package com.ippoippo.joplin.mongo.mapper;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ippoippo.joplin.dto.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	List<User> findByName(String name);
}
