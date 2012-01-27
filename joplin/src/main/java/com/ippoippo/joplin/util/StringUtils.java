package com.ippoippo.joplin.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtils {

	/**
	 * 指定された文字列のMD5ハッシュ値を返す。
	 * @param target 対象文字列
	 * @return 対象文字列のMD5ハッシュ値
	 */
	public static String getMD5Hash(String target) {
		if (target == null) throw new NullPointerException();

		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// 発生しない
			throw new RuntimeException(e);
		}
		msgDigest.update(target.getBytes(), 0, target.length());
		String hash = new BigInteger(1,msgDigest.digest()).toString(16);
		// 32桁に満たない場合は前0埋め
		int length4padding = 32 - hash.length();
		for (int i = 0; i < length4padding; i++) hash = "0"+hash;
		return hash;
	}
}
