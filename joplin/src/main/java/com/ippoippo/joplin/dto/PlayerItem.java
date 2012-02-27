package com.ippoippo.joplin.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class PlayerItem {

	private String id;
	
	@NotEmpty
	private String userId;

	@NotEmpty
	private String articleId;

	@NotEmpty
	private String videoId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
