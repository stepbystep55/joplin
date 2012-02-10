package com.ippoippo.joplin.util;

import javax.crypto.Cipher;

public class Encryptor {

	private Cipher encoder;
	private Cipher decoder;
	
	public Encryptor(Cipher encoder, Cipher decoder) {
		this.encoder = encoder;
		this.decoder = decoder;
	}

	public String encrypt(String target) {
		return StringUtils.encrypt(target, encoder);
	}
	public String decrypt(String target) {
		return StringUtils.decrypt(target, decoder);
	}
}
