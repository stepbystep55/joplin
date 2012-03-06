package com.ippoippo.joplin.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {

	private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

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
}
