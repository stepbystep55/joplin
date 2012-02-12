package com.ippoippo.joplin.social;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ippoippo.joplin.dto.UsersConnection;
import com.ippoippo.joplin.mongo.operations.MongoConnectionOperations;
import com.ippoippo.joplin.util.Encryptor;

public class MongoConnectionRepository implements ConnectionRepository {

	private String userId;
	
	private MongoConnectionOperations mongoConnectionOperations;

	private final ConnectionFactoryLocator connectionFactoryLocator;

	private Encryptor encryptor;
	
	public MongoConnectionRepository(
			String userId
			, MongoConnectionOperations mongoConnectionOperations
			, ConnectionFactoryLocator connectionFactoryLocator
			, Encryptor encryptor
			) {
		this.userId = userId;
		this.mongoConnectionOperations = mongoConnectionOperations;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.encryptor = encryptor;
	}

	@Override
	public void addConnection(Connection<?> connection) {
		try {
			ConnectionData data = connection.createData();

			UsersConnection usersConnection = new UsersConnection();
			usersConnection.setUserId(userId);
			usersConnection.setProviderId(data.getProviderId());
			usersConnection.setProviderUserId(data.getProviderUserId());
			Integer rank = mongoConnectionOperations.getMaxRankByUserIdAndProviderId(userId, data.getProviderId());
			usersConnection.setRank(rank + 1);
			usersConnection.setDisplayName(data.getDisplayName());
			usersConnection.setProfileUrl(data.getProfileUrl());
			usersConnection.setImageUrl(data.getImageUrl());
			usersConnection.setAccessToken(data.getAccessToken());
			usersConnection.setSecret(data.getSecret());
			usersConnection.setRefreshToken(data.getRefreshToken());
			usersConnection.setExpireTime(data.getExpireTime());
			usersConnection.encrypt(encryptor);

			mongoConnectionOperations.raw().save(usersConnection);

		} catch (DuplicateKeyException e) {
			throw new DuplicateConnectionException(connection.getKey());
		}
	}

	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {
		List<UsersConnection> usersConnections = mongoConnectionOperations.listByUserIdSortByProviderIdAndRank(userId);
		List<Connection<?>> resultList = new ArrayList<Connection<?>>();
		for (UsersConnection usersConnection : usersConnections) resultList.add(usersConnection.getConnection(connectionFactoryLocator));
		
		MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
		Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
		for (String registeredProviderId : registeredProviderIds) connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());

		for (Connection<?> connection : resultList) {
			String providerId = connection.getKey().getProviderId();
			if (connections.get(providerId).size() == 0) {
				connections.put(providerId, new LinkedList<Connection<?>>());
			}
			connections.add(providerId, connection);
		}
		return connections;
	}

	@Override
	public List<Connection<?>> findConnections(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <A> List<Connection<A>> findConnections(Class<A> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(
			MultiValueMap<String, String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <A> Connection<A> findPrimaryConnection(Class<A> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection<?> getConnection(ConnectionKey arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <A> Connection<A> getConnection(Class<A> arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeConnection(ConnectionKey arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeConnections(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateConnection(Connection<?> arg0) {
		// TODO Auto-generated method stub

	}

}
