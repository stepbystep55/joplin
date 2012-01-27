package com.ippoippo.joplin.social;

import javax.servlet.http.HttpServletRequest;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ippoippo.joplin.util.UserCookieGenerator;

/**
 * {@link ConnectionSignUp}
 */
public class SimpleConnectionSignUp implements ConnectionSignUp {

	private UserCookieGenerator userCookieGenerator = new UserCookieGenerator();

	public String execute(Connection<?> connection) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String userId = userCookieGenerator.getUserId(request).toString();
		System.out.println("SimpleConnectionSignUp#userId="+userId);
		return userId;
		//return userCookieGenerator.getUserId(request).toString();
		//return connection.getKey().getProviderUserId();
	}

}
