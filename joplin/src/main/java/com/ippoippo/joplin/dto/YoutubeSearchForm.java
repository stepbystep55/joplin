package com.ippoippo.joplin.dto;

import org.hibernate.validator.constraints.Length;

public class YoutubeSearchForm extends PaginationForm {

	@Length(min=1,max=128)
	private String searchText;

	public YoutubeSearchForm() {
		super(10);
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
