package com.ippoippo.joplin.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1080392128618430698L;

	private String id;

	@NotEmpty
	@Length(max=256)
	private String subject;
	
	private boolean active;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", subject=" + subject + "]";
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
