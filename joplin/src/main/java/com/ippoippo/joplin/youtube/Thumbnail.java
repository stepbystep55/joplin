package com.ippoippo.joplin.youtube;

import java.io.Serializable;

import com.google.api.client.util.Key;

public class Thumbnail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8593383000636443222L;

	@Key
	public String sqDefault;
	
	@Key
	public String hqDefault;

	@Override
	public String toString() {
		return "Thumbnail [sqDefault=" + sqDefault + ", hqDefault=" + hqDefault + "]";
	}
}
