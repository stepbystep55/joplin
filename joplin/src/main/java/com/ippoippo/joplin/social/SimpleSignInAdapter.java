package com.ippoippo.joplin.social;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import com.ippoippo.joplin.util.UserCookieForTemporaryGenerator;

/**
 * 
 */
public final class SimpleSignInAdapter implements SignInAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private UserCookieForTemporaryGenerator userCookieGenerator;

	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		logger.info("Signin with userId=" + userId);
		
		HttpServletResponse nativeResponse = (HttpServletResponse)request.getNativeResponse();
		userCookieGenerator.addUserId(nativeResponse, userId);

		return null;
	}

}