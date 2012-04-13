package com.ippoippo.joplin.dto;

import java.io.Serializable;

public class Vote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4330494820814600995L;

	private String userId;

	private String oneItemId;

	private String anotherItemId;

	private String winnerItemId;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOneItemId() {
		return oneItemId;
	}
	public void setOneItemId(String oneItemId) {
		this.oneItemId = oneItemId;
	}
	public String getAnotherItemId() {
		return anotherItemId;
	}
	public void setAnotherItemId(String anotherItemId) {
		this.anotherItemId = anotherItemId;
	}
	public String getWinnerItemId() {
		return winnerItemId;
	}
	public void setWinnerItemId(String winnerItemId) {
		this.winnerItemId = winnerItemId;
	}
}
