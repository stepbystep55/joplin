package com.ippoippo.joplin.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class Match {

	@NotEmpty
	private String articleId;

	private String chosenObjectId;

	private String competitorObjectId;

	@NotEmpty
	private String newChosenObjectId;

	@NotEmpty
	private String discardObjectId;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getChosenObjectId() {
		return chosenObjectId;
	}

	public void setChosenObjectId(String chosenObjectId) {
		this.chosenObjectId = chosenObjectId;
	}

	public String getCompetitorObjectId() {
		return competitorObjectId;
	}

	public void setCompetitorObjectId(String competitorObjectId) {
		this.competitorObjectId = competitorObjectId;
	}

	public String getNewChosenObjectId() {
		return newChosenObjectId;
	}

	public void setNewChosenObjectId(String newChosenObjectId) {
		this.newChosenObjectId = newChosenObjectId;
	}

	public String getDiscardObjectId() {
		return discardObjectId;
	}

	public void setDiscardObjectId(String discardObjectId) {
		this.discardObjectId = discardObjectId;
	}
}
