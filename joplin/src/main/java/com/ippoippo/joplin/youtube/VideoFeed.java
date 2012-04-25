package com.ippoippo.joplin.youtube;

import java.io.Serializable;
import java.util.List;

import com.google.api.client.util.Key;

public class VideoFeed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6164489409063609541L;

	@Key
	public List<Video> items;

	@Override
	public String toString() {
		return "VideoFeed [items=" + items + "]";
	}
}
