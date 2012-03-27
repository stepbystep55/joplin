package com.ippoippo.joplin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.json.JsonCParser;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.ippoippo.joplin.dto.YoutubeItem;
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

	public List<YoutubeItem> search(
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
		
		List<YoutubeItem> items = new ArrayList<YoutubeItem>(feed.items.size());
		for (Video video: feed.items) {
			YoutubeItem item = new YoutubeItem();
			item.setArticleId(articleId);
			item.setVideoId(video.id);
			items.add(item);
		}
		return items;
	}

	public List<YoutubeItem> newMatch(String articleId) {

		List<YoutubeItem> items = this.list(articleId);
		
		// get clones of 2 candidates randomly
		int oneIndex = getIndexRandomly(items.size(), 1);
		int anotherIndex = (oneIndex == 0) ? items.size() - 1 : oneIndex - 1;
		YoutubeItem oneItem = items.get(oneIndex).clone();
		YoutubeItem anotherItem = items.get(anotherIndex).clone();

		List<YoutubeItem> items4match = new ArrayList<YoutubeItem>(2);
		items4match.add(oneItem);
		items4match.add(anotherItem);
		return items4match;
	}
	
	private int getIndexRandomly(int size, int seed) {
		if (seed == 0) return 0;
		return new Random(System.currentTimeMillis() % seed).nextInt(size);
	}
	
	public List<YoutubeItem> list(String articleId) {
		List<YoutubeItem> items = youtubeItemOperations.listByArticleId(articleId);
		Collections.shuffle(items);
		return items;
	}
	
	public void vote(String oneId, String anotherId, String winnerId) {

		List<YoutubeItem> twoItems = youtubeItemOperations.listByIds(Arrays.asList(oneId, anotherId));

		YoutubeItem winnerItem = null;
		YoutubeItem loserItem = null;
		for (YoutubeItem item : twoItems) {
			if (item.getId().equals(winnerId)) {
				winnerItem = item;
			} else {
				loserItem = item;
			}
		}

		winnerItem.calcRateVaried(true, loserItem.getRate());
		loserItem.calcRateVaried(false, winnerItem.getRate());

		youtubeItemOperations.updateRate(winnerItem.getId(), winnerItem.getRateVaried());
		youtubeItemOperations.updateRate(loserItem.getId(), loserItem.getRateVaried());
	}
}
