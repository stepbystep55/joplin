package com.ippoippo.joplin.social;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

import com.ippoippo.joplin.dto.User;

public class MongoUsersConnectionRepository implements UsersConnectionRepository {

	@Inject
	private MongoOperations mongoOperations;
	
	@Inject
	private ConnectionSignUp connectionSignUp;

	public MongoUsersConnectionRepository() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		/*
		ConnectionKey key = connection.getKey();
		List<String> localUserIds = mongoOperations.find(Query.query(Criteria.where("providerUserId").is( key.getProviderId())), User.class);
				//jdbcTemplate.queryForList(
		//"select userId from " + tablePrefix + "UserConnection where providerId = ? and providerUserId = ?", String.class,, key.getProviderUserId());		
		if (localUserIds.size() == 0 && connectionSignUp != null) {
			String newUserId = connectionSignUp.execute(connection);
			if (newUserId != null)
			{
				createConnectionRepository(newUserId).addConnection(connection);
				return Arrays.asList(newUserId);
			}
		}
		return localUserIds;
		*/
		return null;
	}

}
