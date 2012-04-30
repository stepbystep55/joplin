package com.ippoippo.joplin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.json.JsonCParser;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.dto.YoutubeSearchForm;
import com.ippoippo.joplin.mongo.operations.VoteHistoryOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;
import com.ippoippo.joplin.youtube.Video;
import com.ippoippo.joplin.youtube.VideoFeed;
import com.ippoippo.joplin.youtube.YouTubeSearchUrl;

@Service
public class YoutubeSearchService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private HttpRequestFactory gdataRequestFactory;

	@Inject
	YoutubeItemOperations youtubeItemOperations;

	@Inject
	VoteHistoryOperations voteHistoryOperations;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	public List<YoutubeItem> search(String articleId, YoutubeSearchForm youtubeForm) throws IOException {

		// build a YouTube URL
		YouTubeSearchUrl url = new YouTubeSearchUrl();
		url.searchText = youtubeForm.getSearchText();
		url.startIndex = youtubeForm.getStartIndex();
		url.maxResults = youtubeForm.getListSize();

		// build a HTTP GET request
		HttpRequest request = gdataRequestFactory.buildGetRequest(url);
		logger.info("request url: " + request.getUrl().toString());
		request.addParser(new JsonCParser(new JacksonFactory()));

		// execute the request and parse the video feed
		VideoFeed feed = request.execute().parseAs(VideoFeed.class);
		if (feed == null || feed.items == null) return new ArrayList<YoutubeItem>(0);
		
		List<YoutubeItem> items = new ArrayList<YoutubeItem>(feed.items.size());
		for (Video video: feed.items) {
			YoutubeItem item = new YoutubeItem();
			item.setArticleId(articleId);
			item.setVideoId(video.id);
			item.setTitle(video.title);
			item.setThumbnailUrl(video.thumbnail.hqDefault);
			items.add(item);
		}
		return items;
	}
}
