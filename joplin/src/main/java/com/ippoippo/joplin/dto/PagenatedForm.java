package com.ippoippo.joplin.dto;

public abstract class PagenatedForm {

	private Integer startIndex = 1;

	private Integer listSize = 5;

	public PagenatedForm(Integer listSize) {
		this.listSize = listSize;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getListSize() {
		return listSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}

	public void next() {
		this.startIndex = this.startIndex + this.listSize;
	}
	
	public void prev() {
		this.startIndex = this.startIndex - this.listSize;
	}
}
