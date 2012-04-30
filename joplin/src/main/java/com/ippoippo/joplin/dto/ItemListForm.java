package com.ippoippo.joplin.dto;


public class ItemListForm extends PaginationForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7655294652636800957L;

	public static final Integer DEFAULT_LIST_SIZE = 10;

	public ItemListForm() {
		super(DEFAULT_LIST_SIZE);
	}
}
