package com.ippoippo.joplin.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


public abstract class PaginationForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2681416180158216093L;

	public static final Integer DEFAULT_START_INDEX = 1;

	private Integer startIndex = DEFAULT_START_INDEX;

	public static final Integer DEFAULT_LIST_SIZE = 5;

	@Min(5)
	@Max(30)
	private Integer listSize = DEFAULT_LIST_SIZE;
	
	
	private String command = COMMAND_RESET;

	public static final String COMMAND_RESET = "reset";
	public static final String COMMAND_PREV = "previous";
	public static final String COMMAND_NEXT = "next";

	public PaginationForm(Integer listSize) {
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

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public void update() {
		if (this.command.equals(COMMAND_RESET)) {
			this.startIndex = 1;
		} else if (this.command.equals(COMMAND_NEXT)) {
			this.startIndex = this.startIndex + this.listSize;
		} else if (this.command.equals(COMMAND_PREV)) {
			this.startIndex = this.startIndex - this.listSize;
			if (this.startIndex < 1) this.startIndex = 1;
		}
	}
}
