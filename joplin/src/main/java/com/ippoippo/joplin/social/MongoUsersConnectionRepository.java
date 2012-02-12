package com.ippoippo.joplin.social;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

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
import com.ippoippo.joplin.mongo.operations.MongoConnectionOperations;
import com.ippoippo.joplin.util.Encryptor;

public class MongoUsersConnectionRepository implements UsersConnectionRepository {

	private MongoConnectionOperations mongoConnectionOperations;

	private ConnectionFactoryLocator connectionFactoryLocator;
	
	private Encryptor encryptor;

	private ConnectionSignUp connectionSignUp;

	public MongoUsersConnectionRepository(
			MongoConnectionOperations mongoConnectionOperations
			, ConnectionFactoryLocator connectionFactoryLocator
			, Encryptor encryptor
			) {
		super();
		this.mongoConnectionOperations = mongoConnectionOperations;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.encryptor = encryptor;
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		if (userId == null) throw new IllegalArgumentException("userId cannot be null");
		return new MongoConnectionRepository(userId, mongoConnectionOperations, connectionFactoryLocator, encryptor);
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		List<UsersConnection> usersConnections
			= mongoConnectionOperations.listByProviderIdAndProviderUserIds(providerId, providerUserIds);
		Set<String> localUserIds = new HashSet<String>();
		for (UsersConnection usersConnection : usersConnections) localUserIds.add(usersConnection.getUserId());
		return localUserIds;
	}

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {

		ConnectionKey key = connection.getKey();
		
		List<UsersConnection> usersConnections
			= mongoConnectionOperations.listByProviderIdAndProviderUserId(key.getProviderId(), key.getProviderId());

		if (usersConnections.size() == 0 && connectionSignUp != null) {
			String newUserId = connectionSignUp.execute(connection);
			if (newUserId != null) {
				createConnectionRepository(newUserId).addConnection(connection);
				return Arrays.asList(newUserId);
			}
		}
		List<String> localUserIds = new ArrayList<String>();
		for (UsersConnection usersConnection : usersConnections) localUserIds.add(usersConnection.getUserId());
		return localUserIds;
	}

	public ConnectionSignUp getConnectionSignUp() {
		return connectionSignUp;
	}

	public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
		this.connectionSignUp = connectionSignUp;
	}
}
