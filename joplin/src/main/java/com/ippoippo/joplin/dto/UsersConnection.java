package com.ippoippo.joplin.dto;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;

import com.ippoippo.joplin.util.Encryptor;

public class UsersConnection {

	private String userId;
	private String providerId;
	private String providerUserId;
	private Integer rank;
	private String displayName;
	private String profileUrl;
	private String imageUrl;
	private String accessToken;
	private String secret;
	private String refreshToken;
	private Long expireTime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getProviderUserId() {
		return providerUserId;
	}
	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public Long getExpireTime() {
		return (expireTime == 0) ? null : expireTime;
	}
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	public void encrypt(Encryptor encryptor) {
		if (this.accessToken != null) this.accessToken = encryptor.encrypt(this.accessToken);
		if (this.secret != null) this.secret = encryptor.encrypt(this.secret);
		if (this.refreshToken != null) this.refreshToken = encryptor.encrypt(this.refreshToken);
	}
	public void decrypt(Encryptor encryptor) {
		if (this.accessToken != null) this.accessToken = encryptor.decrypt(this.accessToken);
		if (this.secret != null) this.secret = encryptor.decrypt(this.secret);
		if (this.refreshToken != null) this.refreshToken = encryptor.decrypt(this.refreshToken);
	}
	public ConnectionData getConnectionData() {
		return new ConnectionData(
				providerId, providerUserId, displayName, profileUrl, imageUrl,
				accessToken, secret, refreshToken, expireTime);
	}
	public Connection<?> getConnection(ConnectionFactoryLocator connectionFactoryLocator) {
		ConnectionData connectionData = getConnectionData();
		ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
		return connectionFactory.createConnection(connectionData);
	}
	@Override
	public String toString() {
		return "UsersConnection [userId=" + userId + ", providerId="
				+ providerId + ", providerUserId=" + providerUserId + ", rank="
				+ rank + ", displayName=" + displayName + ", profileUrl="
				+ profileUrl + ", imageUrl=" + imageUrl + ", accessToken="
				+ accessToken + ", secret=" + secret + ", refreshToken="
				+ refreshToken + ", expireTime=" + expireTime + "]";
	}
}
