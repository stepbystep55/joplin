package com.ippoippo.joplin.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ippoippo.joplin.dto.UsersConnection;

public interface MongoConnectionRepository extends MongoRepository<UsersConnection, String> {
	
	List<UsersConnection> findByProviderIdAndProviderUserId(String providerId, String providerUserId);
	
	@Query("{providerId: ?0, providerUserId: ?1, $orderby: {rank: 1}, $maxScan: 1}")
	List<UsersConnection> findByProviderIdAndProviderUserIdAndMaxRank(String providerId, String providerUserId);
}
