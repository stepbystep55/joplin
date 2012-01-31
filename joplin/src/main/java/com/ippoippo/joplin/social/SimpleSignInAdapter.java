package com.ippoippo.joplin.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 
 */
public final class SimpleSignInAdapter implements SignInAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		logger.info(this.getClass()+"#signIn()");
		return null;
	}

}