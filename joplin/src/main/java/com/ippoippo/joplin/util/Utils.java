package com.ippoippo.joplin.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

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

	/**
	 * Returns a number randomly.<br/>
	 * This function use internally java.util.Random.
	 * @param size the bound on the random number to be returned. Must be positive.
	 * @param excluded the number you want to exclude. if none, set negative number (ex. -1).
	 * @return random number (int value between 0 (inclusive) and size(exclusive))
	 */
	public static int getIntRandomly(int size, int excluded) {
		if (size < 1) throw new RuntimeException("size is too small.");

		int ret = 0;
		for (int i = 1; i <= 10; i++) {
			long seed = (long)(System.currentTimeMillis() / i);
			ret = new Random(seed).nextInt(size);
			if (ret != excluded) break;
		}

		if (ret == excluded) {
			// if cant get different, set the next of excluded.
			ret = (excluded == 0) ? (size - 1) : excluded - 1;
		}
		return ret;
	}

	/**
	 * オブジェクトをシリアライズ（byte配列値化）する。
	 * @param obj シリアライズ対象オブジェクト
	 * @return シリアライズされたオブジェクト
	 */
	public static byte[] serialize(Serializable obj) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.reset();

		} catch (IOException e) {
			throw new RuntimeException("Fail to serialize a object. object="+obj, e);
		} finally {
			try {
				if (oos != null) oos.close();
			} catch (IOException e) {
				logger.error("Fail to close a stream.", e);
			}
		}
		return bos.toByteArray();
	}
	
	/**
	 * シリアライズされたオブジェクトを復元します。
	 * @param bytes シリアライズされたオブジェクト
	 * @return 復元したオブジェクト
	 */
	public static Object deserialize(byte[] bytes) {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return in.readObject();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Fail to deserialize a object.", e);
		} catch (IOException e) {
			throw new RuntimeException("Fail to deserialize a object.", e);
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {
				logger.error("Fail to close a stream.", e);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(args[0]+"="+Utils.hashMD5(args[0]));
	}
}
