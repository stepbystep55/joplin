package com.ippoippo.joplin.dto;

import org.hibernate.validator.constraints.Length;

public class YoutubeSearchForm extends PagenatedForm {

	private String articleId;

	@Length(min=1,max=128)
	private String searchText;

	private String command;

	public YoutubeSearchForm() {
		super(10);
	}

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

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
}
