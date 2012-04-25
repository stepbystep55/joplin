package com.ippoippo.joplin.youtube;

import java.io.Serializable;

import com.google.api.client.util.Key;

public class Video implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5220827481214033392L;

	@Key
	public String id;
	
	@Key
	public String title;
	
	@Key
	public Integer viewCount;
	
	@Key
	public Thumbnail thumbnail;

	@Override
	public String toString() {
		return "Video [id=" + id + ", title=" + title + ", viewCount="
				+ viewCount + ", thumbnail=" + thumbnail + "]";
	}
}
