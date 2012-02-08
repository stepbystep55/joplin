package com.ippoippo.joplin.util;

import javax.crypto.Cipher;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.CookieGenerator;

public class UserCookieForTemporaryGenerator {

	public static final String SESSION_KEY_AUTH = "uid";

	@Inject
	private Cipher encoder;

	@Inject
	private Cipher decoder;

	private CookieGenerator userIdCookieGenerator = new CookieGenerator();

	private static final int ONE_WEEK = 7 * 24 * 60 * 60; // for 1 week

	public UserCookieForTemporaryGenerator() {
		this.userIdCookieGenerator.setCookieMaxAge(-1);
		this.userIdCookieGenerator.setCookieName(SESSION_KEY_AUTH);
	}

	public void addUserId(HttpServletResponse response, String userId) {
		this.userIdCookieGenerator.addCookie(response, StringUtils.encrypt(userId, encoder));
	}

	public void removeUserId(HttpServletResponse response) {
		this.userIdCookieGenerator.removeCookie(response);
	}

	public String getUserId(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) return null;

		String userId = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(SESSION_KEY_AUTH)) {
				userId = StringUtils.decrypt(cookie.getValue(), decoder);
				break;
			}
		}
		return userId;
	}
}
