package com.ippoippo.joplin.social;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import com.ippoippo.joplin.service.AuthService;
import com.ippoippo.joplin.service.FriendService;

/**
 * 
 */
public final class SimpleSignInAdapter implements SignInAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	AuthService authService;

	@Inject
	private FriendService friendService;

	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		
		HttpServletResponse httpResponse = request.getNativeResponse(HttpServletResponse.class);
		HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
		authService.setUserId(httpRequest, httpResponse, userId);

		friendService.updateFriends(userId, connection);

		logger.info("Signin with userId=" + userId);
		return null;
	}
}