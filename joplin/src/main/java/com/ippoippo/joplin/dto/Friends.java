package com.ippoippo.joplin.dto;

import java.io.Serializable;
import java.util.Set;

public class Friends implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 21097457495289085L;

	private String id;
	
	private String providerId;
	
	private String userId;
	
	private Set<String> friendIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<String> getFriendIds() {
		return friendIds;
	}

	public void setFriendIds(Set<String> friendIds) {
		this.friendIds = friendIds;
	}
}
