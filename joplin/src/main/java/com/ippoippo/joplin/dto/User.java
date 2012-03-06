package com.ippoippo.joplin.dto;

import java.io.Serializable;



public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2633278287071100378L;

	private String id;

	private String name = "anonymous";
	
	private String email = "none@no.where";

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
}
