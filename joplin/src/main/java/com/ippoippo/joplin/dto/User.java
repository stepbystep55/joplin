package com.ippoippo.joplin.dto;



public class User {

	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";

	private Integer id = null;

	private String name = "anonymouos";
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
