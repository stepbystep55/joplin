package com.ippoippo.joplin.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ippoippo.joplin.util.UserCookieForTemporaryGenerator;

@Service
public class AuthService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	UserCookieForTemporaryGenerator userCookieForTemporaryGenerator;

	public String getUserId(HttpServletRequest request) {
		String userId = userCookieForTemporaryGenerator.getUserId(request);
		return userId;
	}

	public void setUserId(HttpServletRequest request, HttpServletResponse response, String userId) {
		userCookieForTemporaryGenerator.addUserId(response, userId);
	}

	public void removeUserId(HttpServletRequest request, HttpServletResponse response) {
		userCookieForTemporaryGenerator.removeUserId(response);
	}
}
