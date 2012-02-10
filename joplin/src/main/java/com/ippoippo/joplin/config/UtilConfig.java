package com.ippoippo.joplin.config;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ippoippo.joplin.util.Encryptor;
import com.ippoippo.joplin.util.UserCookieForTemporaryGenerator;

/**
 * Utility Configuration.
 */
@Configuration
public class UtilConfig {

	@Value("${cipher.key}")
	private String cipherKey;

	@Bean
	public UserCookieForTemporaryGenerator userCookieForTemporaryGenerator() {
		return new UserCookieForTemporaryGenerator(encryptor());
	}
	
	@Bean
	public Encryptor encryptor() {
		Cipher encoder = null;
		Cipher decoder = null;
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
		return new Encryptor(encoder, decoder);
	}
}