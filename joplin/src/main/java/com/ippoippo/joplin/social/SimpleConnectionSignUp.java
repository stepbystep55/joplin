package com.ippoippo.joplin.social;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.transaction.annotation.Transactional;

import com.ippoippo.joplin.dto.User;
import com.ippoippo.joplin.mongo.mapper.UserRepository;

/**
 * {@link ConnectionSignUp}
 */
public class SimpleConnectionSignUp implements ConnectionSignUp {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
/*
	@Inject
	private UserMasterMapper userMasterMapper;
*/
	@Inject
	private MongoOperations mongoOperations;

	@Transactional(rollbackForClassName="java.lang.Exception")
	public String execute(Connection<?> connection) {
		
		User user = new User();
		/*
		String userId = userMasterMapper.newId().toString();
		user.setId(userId);
		userMasterMapper.createUser(user);
		*/
		mongoOperations.save(user);
		String userId = user.getId();

		logger.info("Signup with userId=" + userId);
		return userId;
	}
}
