package com.ippoippo.joplin.dto;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotEmpty;

public class MatchResult {

	private String id;
	
	@NotEmpty
	private String userId;

	@NotEmpty
	private String chosenItemId;

	@NotEmpty
	private String discardItemId;
	
	private Timestamp matchAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChosenItemId() {
		return chosenItemId;
	}

	public void setChosenItemId(String chosenItemId) {
		this.chosenItemId = chosenItemId;
	}

	public String getDiscardItemId() {
		return discardItemId;
	}

	public void setDiscardItemId(String discardItemId) {
		this.discardItemId = discardItemId;
	}

	public Timestamp getMatchAt() {
		return matchAt;
	}

	public void setMatchAt(Timestamp matchAt) {
		this.matchAt = matchAt;
	}
}