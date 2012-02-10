package com.ippoippo.joplin.social;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.util.MultiValueMap;

import com.ippoippo.joplin.util.Encryptor;

public class MongoConnectionRepository implements ConnectionRepository {

	private String userId;
	
	private MongoOperations mongoOperations;
	
	private ConnectionFactoryLocator connectionFactoryLocator;
	
	private Encryptor encryptor;

	public MongoConnectionRepository(
			String userId
			, MongoOperations mongoOperations
			, ConnectionFactoryLocator connectionFactoryLocator
			, Encryptor encryptor
			) {
		this.userId = userId;
		this.mongoOperations = mongoOperations;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.encryptor = encryptor;
	}

	@Override
	public void addConnection(Connection<?> arg0) {
		try {
			ConnectionData data = connection.createData();
			int rank = jdbcTemplate.queryForInt("select coalesce(max(rank) + 1, 1) as rank from " + tablePrefix + "UserConnection where userId = ? and providerId = ?", userId, data.getProviderId());
			jdbcTemplate.update("insert into " + tablePrefix + "UserConnection (userId, providerId, providerUserId, rank, displayName, profileUrl, imageUrl, accessToken, secret, refreshToken, expireTime) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					userId, data.getProviderId(), data.getProviderUserId(), rank, data.getDisplayName(), data.getProfileUrl(), data.getImageUrl(), encrypt(data.getAccessToken()), encrypt(data.getSecret()), encrypt(data.getRefreshToken()), data.getExpireTime());
		} catch (DuplicateKeyException e) {
			throw new DuplicateConnectionException(connection.getKey());
		}
	}

	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {
		List<Connection<?>> resultList = jdbcTemplate.query(selectFromUserConnection() + " where userId = ? order by providerId, rank", connectionMapper, userId);
		MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
		Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
		for (String registeredProviderId : registeredProviderIds) {
			connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
		}
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
		return jdbcTemplate.query(selectFromUserConnection() + " where userId = ? and providerId = ? order by rank", connectionMapper, userId, providerId);
	}

	@Override
	public <A> List<Connection<A>> findConnections(Class<A> arg0) {
		List<?> connections = findConnections(getProviderId(apiType));
		return (List<Connection<A>>) connections;
	}

	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(
			if (providerUsers == null || providerUsers.isEmpty()) {
				throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
			}
			StringBuilder providerUsersCriteriaSql = new StringBuilder();
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("userId", userId);
			for (Iterator<Entry<String, List<String>>> it = providerUsers.entrySet().iterator(); it.hasNext();) {
				Entry<String, List<String>> entry = it.next();
				String providerId = entry.getKey();
				providerUsersCriteriaSql.append("providerId = :providerId_").append(providerId).append(" and providerUserId in (:providerUserIds_").append(providerId).append(")");
				parameters.addValue("providerId_" + providerId, providerId);
				parameters.addValue("providerUserIds_" + providerId, entry.getValue());
				if (it.hasNext()) {
					providerUsersCriteriaSql.append(" or " );
				}
			}
			List<Connection<?>> resultList = new NamedParameterJdbcTemplate(jdbcTemplate).query(selectFromUserConnection() + " where userId = :userId and " + providerUsersCriteriaSql + " order by providerId, rank", parameters, connectionMapper);
			MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<String, Connection<?>>();
			for (Connection<?> connection : resultList) {
				String providerId = connection.getKey().getProviderId();
				List<String> userIds = providerUsers.get(providerId);
				List<Connection<?>> connections = connectionsForUsers.get(providerId);
				if (connections == null) {
					connections = new ArrayList<Connection<?>>(userIds.size());
					for (int i = 0; i < userIds.size(); i++) {
						connections.add(null);
					}
					connectionsForUsers.put(providerId, connections);
				}
				String providerUserId = connection.getKey().getProviderUserId();
				int connectionIndex = userIds.indexOf(providerUserId);
				connections.set(connectionIndex, connection);
			}
			return connectionsForUsers;
	}

	@Override
	public <A> Connection<A> findPrimaryConnection(Class<A> arg0) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) findPrimaryConnection(providerId);
	}

	@Override
	public Connection<?> getConnection(ConnectionKey arg0) {
		try {
			return jdbcTemplate.queryForObject(selectFromUserConnection() + " where userId = ? and providerId = ? and providerUserId = ?", connectionMapper, userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchConnectionException(connectionKey);
		}
	}

	@Override
	public <A> Connection<A> getConnection(Class<A> arg0, String arg1) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
	}

	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> arg0) {
		String providerId = getProviderId(apiType);
		Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
		if (connection == null) {
			throw new NotConnectedException(providerId);
		}
		return connection;
	}

	@Override
	public void removeConnection(ConnectionKey arg0) {
		jdbcTemplate.update("delete from " + tablePrefix + "UserConnection where userId = ? and providerId = ? and providerUserId = ?", userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());		
	}

	@Override
	public void removeConnections(String arg0) {
		jdbcTemplate.update("delete from " + tablePrefix + "UserConnection where userId = ? and providerId = ?", userId, providerId);
	}

	@Override
	public void updateConnection(Connection<?> arg0) {
		ConnectionData data = connection.createData();
		jdbcTemplate.update("update " + tablePrefix + "UserConnection set displayName = ?, profileUrl = ?, imageUrl = ?, accessToken = ?, secret = ?, refreshToken = ?, expireTime = ? where userId = ? and providerId = ? and providerUserId = ?",
				data.getDisplayName(), data.getProfileUrl(), data.getImageUrl(), encrypt(data.getAccessToken()), encrypt(data.getSecret()), encrypt(data.getRefreshToken()), data.getExpireTime(), userId, data.getProviderId(), data.getProviderUserId());
	
	}
}
