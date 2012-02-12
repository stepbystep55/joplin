package com.ippoippo.joplin.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;

public class StringUtils {

	/**
	 * 指定された文字列のMD5ハッシュ値を返す。
	 * @param target 対象文字列
	 * @return 対象文字列のMD5ハッシュ値
	 */
	public static String hashMD5(String target) {
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

	public static String encrypt(String target, Cipher encoder) {
		byte[] bytes = null;
		try {
			bytes = encoder.doFinal(target.toString().getBytes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return Hex.encodeHexString(bytes);
	}

	public static String decrypt(String target, Cipher decoder) {
		byte[] bytes = null;
		try {
			bytes = decoder.doFinal(Hex.decodeHex(target.toCharArray()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new String(bytes);
	}
}
