package com.ippoippo.joplin.dto;

import org.hibernate.validator.constraints.Length;

public class YoutubeSearchForm extends PagenatedForm {

	private String articleId;
	
	@Length(min=1,max=128)
	private String searchText;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}