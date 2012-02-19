package com.ippoippo.joplin.dto;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;

public class UserDisplay {

	private String providerName;
	private String name;
	private String imageUrl;
	private String profileUrl;

	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	
	/**
	 * 
	 * @param connectionRepository
	 * @return if no connection found, return false.
	 */
	public boolean setWithConnectionRepository(ConnectionRepository connectionRepository) {

		Connection connection = null;
		// facebook is primary, twitter is secondary
		if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
			connection = connectionRepository.getPrimaryConnection(Facebook.class);
		} else {
			if (connectionRepository.findPrimaryConnection(Twitter.class) != null) {
				connection = connectionRepository.getPrimaryConnection(Twitter.class);
			}
		}
		if (connection == null) return false;
			
		setName(connection.getDisplayName());
		setImageUrl(connection.getImageUrl());
		setProfileUrl(connection.getProfileUrl());
		
		return true;
	}
}
