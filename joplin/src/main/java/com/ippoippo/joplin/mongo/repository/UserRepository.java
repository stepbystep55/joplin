package com.ippoippo.joplin.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ippoippo.joplin.dto.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	@Query("{name: ?0}")
	List<User> findMine(String name);
	
	List<User> findByName(String name);

	List<User> findByNameAndEmail(String name, String email);
}