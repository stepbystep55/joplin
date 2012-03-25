package com.ippoippo.joplin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.json.JsonCParser;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.youtube.Video;
import com.ippoippo.joplin.youtube.VideoFeed;
import com.ippoippo.joplin.youtube.YouTubeSearchUrl;

@Service
public class YoutubeSearchService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private HttpRequestFactory gdataRequestFactory;


	public List<YoutubeItem> searchItems(
			String articleId
			, String searchText
			, Integer startIndex
			, Integer listSize) throws IOException {

		// build the YouTube URL
		YouTubeSearchUrl url = new YouTubeSearchUrl();
		url.searchText = searchText;
		url.startIndex = startIndex;
		url.maxResults = listSize;

		// build the HTTP GET request
		HttpRequest request = gdataRequestFactory.buildGetRequest(url);
		request.addParser(new JsonCParser(new JacksonFactory()));

		logger.info("request url: " + request.getUrl().toString());

		// execute the request and the parse video feed
		VideoFeed feed = request.execute().parseAs(VideoFeed.class);
		
		// extract video ids
		List<String> videoIds = new ArrayList<String>();
		for (Video video : feed.items) videoIds.add(video.id);

		List<YoutubeItem> items = new ArrayList<YoutubeItem>(videoIds.size());
		for (String videoId : videoIds) {
			YoutubeItem item = new YoutubeItem();
			item.setArticleId(articleId);
			item.setVideoId(videoId);
			items.add(item);
		}
		return items;
	}

}
