package com.ippoippo.joplin.social;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

import com.ippoippo.joplin.dto.User;
import com.ippoippo.joplin.dto.UsersConnection;
import com.ippoippo.joplin.util.Encryptor;

public class MongoUsersConnectionRepository implements UsersConnectionRepository {

	private MongoOperations mongoOperations;

	private ConnectionFactoryLocator connectionFactoryLocator;
	
	private Encryptor encryptor;

	private ConnectionSignUp connectionSignUp;

	public MongoUsersConnectionRepository(
			MongoOperations mongoOperations
			, ConnectionFactoryLocator connectionFactoryLocator
			, Encryptor encryptor
			) {
		super();
		this.mongoOperations = mongoOperations;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.encryptor = encryptor;
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		if (userId == null) throw new IllegalArgumentException("userId cannot be null");
		return new MongoConnectionRepository(userId, mongoOperations, connectionFactoryLocator, encryptor);
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		Set<String> localUserIds = new HashSet<String>();
		List<UsersConnection> userConnections
			= mongoOperations.find(
					Query.query(
						Criteria.where("providerId").is(providerId).and("providerUserId").in(providerUserIds))
					, UsersConnection.class);
		for (int i = 0; i < userConnections.size(); i++) localUserIds.add(userConnections.get(i).getUserId());
		return localUserIds;
	}

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {

		ConnectionKey key = connection.getKey();
		
		List<User> localUsers
			= mongoOperations.find(
					Query.query(
						Criteria.where("providerId").is(key.getProviderId()).and("providerUserId").is( key.getProviderId()))
					, User.class);

		if (localUsers.size() == 0 && connectionSignUp != null) {
			String newUserId = connectionSignUp.execute(connection);
			if (newUserId != null) {
				createConnectionRepository(newUserId).addConnection(connection);
				return Arrays.asList(newUserId);
			}
		}
		List<String> localUserIds = new ArrayList<String>();
		for (int i = 0; i < localUsers.size(); i++) localUserIds.add(localUsers.get(i).getId());
		return localUserIds;
	}

	public ConnectionSignUp getConnectionSignUp() {
		return connectionSignUp;
	}

	public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
		this.connectionSignUp = connectionSignUp;
	}
}
