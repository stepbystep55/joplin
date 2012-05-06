package com.ippoippo.joplin.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.CookieGenerator;

public class UserCookieForTemporaryGenerator {

	public static final String KEY_USERID = "uid";

	private Encryptor encryptor;

	private CookieGenerator userIdCookieGenerator = new CookieGenerator();

	private static final int ONE_WEEK = 7 * 24 * 60 * 60; // for 1 week

	public UserCookieForTemporaryGenerator(Encryptor encryptor) {
		this.encryptor = encryptor;
		this.userIdCookieGenerator.setCookieMaxAge(-1);
		//this.userIdCookieGenerator.setCookieMaxAge(ONE_WEEK);
		this.userIdCookieGenerator.setCookieName(KEY_USERID);
	}

	public void addUserId(HttpServletResponse response, String userId) {
		this.userIdCookieGenerator.addCookie(response, encryptor.encrypt(userId));
	}

	public void removeUserId(HttpServletResponse response) {
		this.userIdCookieGenerator.removeCookie(response);
	}

	public String getUserId(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) return null;

		String userId = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(KEY_USERID)) {
				userId = encryptor.decrypt(cookie.getValue());
				break;
			}
		}
		return userId;
	}
}
