package com.ippoippo.joplin.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ippoippo.joplin.cookie.FappCookie;
import com.ippoippo.joplin.cookie.UserCookieForTemporary;
import com.ippoippo.joplin.jdbc.mapper.UserMasterMapper;
import com.ippoippo.joplin.mongo.operations.ContributionOperations;
import com.ippoippo.joplin.mongo.operations.VoteHistoryOperations;

@Service
public class AuthService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	UserCookieForTemporary userCookie;

	@Inject
	FappCookie fappCookie;

	@Inject
	ContributionOperations contributionOperations;

	@Inject
	VoteHistoryOperations voteHistoryOperations;

	@Inject
	UserMasterMapper userMasterMapper;

	public String getUserId(HttpServletRequest request) {
		String userId = userCookie.getUserId(request);
		return userId;
	}

	public void setUserId(HttpServletResponse response, String userId) {
		userCookie.addUserId(response, userId);
	}

	public void removeUserId(HttpServletResponse response) {
		userCookie.removeUserId(response);
	}

	public void setFappTrue(HttpServletResponse response) {
		fappCookie.setTrue(response);
	}

	public boolean isFapp(HttpServletRequest request) {
		return fappCookie.isTrue(request);
	}
	
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
		String userId = userCookie.getUserId(request);
		contributionOperations.deleteByUserId(userId);
		voteHistoryOperations.deleteByUserId(userId);
		userMasterMapper.delete(userId);
		userCookie.removeUserId(response);
	}
}
