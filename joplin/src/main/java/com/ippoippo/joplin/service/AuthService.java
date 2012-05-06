package com.ippoippo.joplin.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		if (userId == null) {
			// IE & Safari doesn't accept third party cookies (occurred when iframe) then like this
			userId = (String)request.getSession().getAttribute(UserCookieForTemporaryGenerator.KEY_USERID);
		}
		return userId;
	}

	public void setUserId(HttpServletRequest request, HttpServletResponse response, String userId) {
		//userCookieForTemporaryGenerator.addUserId(response, userId); // renew cookie for extending maxage
		// IE & Safari doesn't accept third party cookies (occurred when iframe) then like this
		request.getSession().setAttribute(UserCookieForTemporaryGenerator.KEY_USERID, userId);
	}

	public void removeUserId(HttpServletRequest request, HttpServletResponse response) {
		userCookieForTemporaryGenerator.removeUserId(response);
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(UserCookieForTemporaryGenerator.KEY_USERID);
			try {
				session.invalidate();
			} catch (IllegalStateException e) {
				logger.error(e.toString(), e);
			}
		}
	}
}
