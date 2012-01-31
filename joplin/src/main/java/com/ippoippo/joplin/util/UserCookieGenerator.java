package com.ippoippo.joplin.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.CookieGenerator;

public class UserCookieGenerator {

	@Value("${cipher.key}")
	private String cipherKey;

	public static final String SESSION_KEY_AUTH = "uid";

	private Cipher encoder = null;

	private Cipher decoder = null;

	private CookieGenerator userIdCookieGenerator = new CookieGenerator();

	private static final int ONE_WEEK = 7 * 24 * 60 * 60;

	private void initCipher() {
		if (encoder == null || decoder == null) {
			try {
				SecretKey secretKey
				= SecretKeyFactory.getInstance("DESede").generateSecret(new DESedeKeySpec(cipherKey.getBytes()));

				encoder = Cipher.getInstance("DESede");
				encoder.init(Cipher.ENCRYPT_MODE, secretKey);

				decoder = Cipher.getInstance("DESede");
				decoder.init(Cipher.DECRYPT_MODE, secretKey);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private String encrypt(Integer target) {
		initCipher();
		byte[] bytes = null;
		try {
			bytes = encoder.doFinal(target.toString().getBytes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return Hex.encodeHexString(bytes);
	}

	private Integer decrypt(String target) {
		initCipher();
		byte[] bytes = null;
		try {
			bytes = Hex.decodeHex(target.toCharArray());
			decoder.doFinal(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new Integer(new String(bytes));
	}

	public UserCookieGenerator() {
		this.userIdCookieGenerator.setCookieName(SESSION_KEY_AUTH);
	}

	public void addUserIdForTemporary(HttpServletResponse response, Integer userId) {
		this.userIdCookieGenerator.setCookieMaxAge(-1);
		this.userIdCookieGenerator.addCookie(response, ""+encrypt(userId));
	}

	public void addUserIdForOneWeek(HttpServletResponse response, Integer userId) {
		this.userIdCookieGenerator.setCookieMaxAge(ONE_WEEK);
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
