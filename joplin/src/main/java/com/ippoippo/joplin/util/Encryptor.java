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
		return Utils.encrypt(target, encoder);
	}
	public String decrypt(String target) {
		return Utils.decrypt(target, decoder);
	}
}
