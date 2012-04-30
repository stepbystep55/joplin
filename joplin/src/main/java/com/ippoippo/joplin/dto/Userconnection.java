package com.ippoippo.joplin.dto;

import java.io.Serializable;

public class Userconnection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4055349890050814447L;

	private String userId;

	private String providerId;

	private String providerUserid;

	private String displayName;

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
	public String getProviderUserid() {
		return providerUserid;
	}
	public void setProviderUserid(String providerUserid) {
		this.providerUserid = providerUserid;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
