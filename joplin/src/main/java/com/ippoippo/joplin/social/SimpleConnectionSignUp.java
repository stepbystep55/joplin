package com.ippoippo.joplin.social;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.transaction.annotation.Transactional;

import com.ippoippo.joplin.dto.User;
import com.ippoippo.joplin.dto.UsersConnection;
import com.ippoippo.joplin.jdbc.mapper.UserMasterMapper;
import com.ippoippo.joplin.mongo.operations.MongoConnectionOperations;

/**
 * {@link ConnectionSignUp}
 */
public class SimpleConnectionSignUp implements ConnectionSignUp {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Inject
	private UserMasterMapper userMasterMapper;
/*
	@Inject
	private UserRepository userRepository;

	@Inject
	private MongoConnectionRepository mongoConnectionRepository;
*/
	/*
	@Inject
	private MongoConnectionOperations mongoConnectionOperations;
	*/

	@Transactional(rollbackForClassName="java.lang.Exception")
	public String execute(Connection<?> connection) {
		
		User user = new User();
		Integer userId = userMasterMapper.newId();
		if (userId == null) userId = new Integer(1);
		user.setId(userId.toString());
		userMasterMapper.create(user);
		/*
		User user = userRepository.save(new User());
		String userId = user.getId();
		List<User> users = userRepository.findMine("anonymous");
		logger.info("users=" + users);
		*/

		/*
		ConnectionData data = connection.createData();
		Integer rank = mongoConnectionOperations.getMaxRankByUserIdAndProviderId(userId, data.getProviderId());
		UsersConnection usersConnection = new UsersConnection();
		usersConnection.setUserId(userId);
		usersConnection.setProviderId(data.getProviderId());
		usersConnection.setProviderUserId(data.getProviderUserId());
		usersConnection.setRank(rank + 1);
		usersConnection.setDisplayName(data.getDisplayName());
		usersConnection.setProfileUrl(data.getProfileUrl());
		usersConnection.setImageUrl(data.getImageUrl());
		usersConnection.setAccessToken(data.getAccessToken());
		usersConnection.setSecret(data.getSecret());
		usersConnection.setRefreshToken(data.getRefreshToken());
		usersConnection.setExpireTime(data.getExpireTime());
		mongoConnectionOperations.raw().save(usersConnection);
		rank = mongoConnectionOperations.getMaxRankByUserIdAndProviderId(userId, data.getProviderId());
		logger.info("cons="+rank);
		*/

		logger.info("Signup with userId=" + userId);
		return userId.toString();
	}
}
