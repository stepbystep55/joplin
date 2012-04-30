package com.ippoippo.joplin.dto;

import java.io.Serializable;

public class Vote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3974193121196731732L;

	private String articleId;

	private String userId;

	private String oneItemId;

	private String anotherItemId;

	private String winnerItemId;

	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
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
