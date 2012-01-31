package com.ippoippo.joplin.social;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.transaction.annotation.Transactional;

import com.ippoippo.joplin.dto.User;
import com.ippoippo.joplin.mapper.UserMasterMapper;
import com.ippoippo.joplin.util.UserCookieGenerator;

/**
 * {@link ConnectionSignUp}
 */
public class SimpleConnectionSignUp implements ConnectionSignUp {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private UserMasterMapper userMasterMapper;

	private UserCookieGenerator userCookieGenerator = new UserCookieGenerator();

	@Transactional(rollbackForClassName="java.lang.Exception")
	public String execute(Connection<?> connection) {
		
		User user = new User();
		Integer userId = userMasterMapper.newId();
		user.setId(userMasterMapper.newId());
		userMasterMapper.createUser(user);
		
		logger.info("SimpleConnectionSignUp#userId="+userId);
		return userId.toString();
	}

}
