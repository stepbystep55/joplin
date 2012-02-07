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

	private String encrypt(Integer target) {
		return StringUtils.encrypt(target.toString(), encoder);
	}

	private Integer decrypt(String target) {
		return new Integer(StringUtils.decrypt(target, decoder));
	}

	public void addUserId(HttpServletResponse response, Integer userId) {
		this.userIdCookieGenerator.addCookie(response, ""+encrypt(userId));
	}

	public void removeUserId(HttpServletResponse response) {
		this.userIdCookieGenerator.removeCookie(response);
	}

	public Integer getUserId(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) return null;

		Integer userId = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(SESSION_KEY_AUTH)) {
				userId = decrypt(cookie.getValue());
				break;
			}
		}
		return userId;
	}
}
