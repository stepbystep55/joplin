package com.ippoippo.joplin.youtube;

import com.google.api.client.googleapis.GoogleUrl;
import com.google.api.client.util.Key;

/**
 * URL for search youtube videos.
 */
public class YouTubeSearchUrl extends GoogleUrl {

	/** Whether to pretty print HTTP requests and responses. */
	private static final boolean PRETTY_PRINT = true;

	public static final String ORDERBY_RELEVANCE = "relevance";
	public static final String ORDERBY_PUBLISHED = "published";
	public static final String ORDERBY_VIEWCOUNT = "viewCount";
	public static final String ORDERBY_RATING = "rating";
	
	private static final String BASE_URL = "https://gdata.youtube.com/feeds/api/videos";
	private static final Integer VERSION = 2;

	@Key("q")
	public String searchText;

	@Key("start-index")
	public Integer startIndex = 1;
	
	@Key("max-results")
	public Integer maxResults = 1;

	@Key("orderby")
	public String orderby = ORDERBY_RELEVANCE;
	
	@Key("v")
	public Integer version = VERSION;

	public YouTubeSearchUrl() {
		super(BASE_URL);
		this.alt = "jsonc";
		this.prettyprint = PRETTY_PRINT;
	}
}
