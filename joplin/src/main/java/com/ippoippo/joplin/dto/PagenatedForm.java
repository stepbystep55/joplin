package com.ippoippo.joplin.dto;

public class PagenatedForm {

	private Integer startIndex = 1;

	private Integer listSize = 5;

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
