package com.ippoippo.joplin.youtube;

import java.util.List;

import com.google.api.client.util.Key;

public class Video {

	@Key
	public String id;
	
	@Key
	public String title;
	
	@Key
	public Integer viewCount;
	
	@Key
	public Thumbnail thumbnail;
}
