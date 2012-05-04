package com.ippoippo.joplin.social;

import java.text.MessageFormat;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.transaction.annotation.Transactional;

import com.ippoippo.joplin.dto.User;
import com.ippoippo.joplin.jdbc.mapper.UserMasterMapper;

/**
 * {@link ConnectionSignUp}
 */
public class SimpleConnectionSignUp implements ConnectionSignUp {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private UserMasterMapper userMasterMapper;

	@Value("${home.url}")
	private String homeUrl;

	@Value("${application.name}")
	private String applicationName;

	@Value("${application.caption}")
	private String applicationCaption;

	@Value("${application.description}")
	private String applicationDescription;

	@Transactional(rollbackForClassName="java.lang.Exception")
	public String execute(Connection<?> connection) {
		
		User user = new User();
		Integer userId = userMasterMapper.newId();
		if (userId == null) userId = new Integer(1);
		user.setId(userId.toString());
		userMasterMapper.create(user);
		
		Object api = connection.getApi();
		if (api instanceof Facebook) {
			String msg = MessageFormat.format("{0} starts using {1}.", connection.getDisplayName(), applicationName);
			FeedOperations feedOperations = ((Facebook)api).feedOperations();
			feedOperations.postLink(msg, new FacebookLink(homeUrl, applicationName, applicationCaption, applicationDescription));

		//} else if (api instanceof Twitter) {
		}
		logger.info("Signup with userId=" + userId);
		return userId.toString();
	}
}
