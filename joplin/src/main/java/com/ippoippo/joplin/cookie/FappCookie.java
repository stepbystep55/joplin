package com.ippoippo.joplin.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.CookieGenerator;

public class FappCookie {

	public static final String KEY_FAPP = "fapp";

	private CookieGenerator fappCookieGenerator = new CookieGenerator();

	public FappCookie() {
		this.fappCookieGenerator.setCookieMaxAge(-1);
		this.fappCookieGenerator.setCookieName(KEY_FAPP);
	}

	public void setTrue(HttpServletResponse response) {
		this.fappCookieGenerator.addCookie(response, "true");
	}

	public void remove(HttpServletResponse response) {
		this.fappCookieGenerator.removeCookie(response);
	}

	public boolean isTrue(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) return false;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(KEY_FAPP)) return Boolean.parseBoolean(cookie.getValue());
		}
		return false;
	}
}
