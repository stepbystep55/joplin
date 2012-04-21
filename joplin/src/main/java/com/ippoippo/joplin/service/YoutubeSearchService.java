package com.ippoippo.joplin.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.json.JsonCParser;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.ippoippo.joplin.dto.Vote;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.dto.YoutubeSearchForm;
import com.ippoippo.joplin.mongo.operations.VoteHistoryOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;
import com.ippoippo.joplin.util.Utils;
import com.ippoippo.joplin.youtube.Video;
import com.ippoippo.joplin.youtube.VideoFeed;
import com.ippoippo.joplin.youtube.YouTubeSearchUrl;

@Service
public class YoutubeSearchService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${home.url}")
	private String homeUrl;

	@Value("${application.name}")
	private String applicationName;

	@Value("${application.caption}")
	private String applicationCaption;

	@Value("${application.description}")
	private String applicationDescription;

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
		request.addParser(new JsonCParser(new JacksonFactory()));

		logger.info("request url: " + request.getUrl().toString());

		// execute the request and parse the video feed
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
		int oneIndex = Utils.getIntRandomly(items.size(), -1);
		int anotherIndex = Utils.getIntRandomly(items.size(), oneIndex);
		YoutubeItem oneItem = items.get(oneIndex).clone();
		YoutubeItem anotherItem = items.get(anotherIndex).clone();

		List<YoutubeItem> items4match = new ArrayList<YoutubeItem>(2);
		items4match.add(oneItem);
		items4match.add(anotherItem);
		return items4match;
	}

	public List<YoutubeItem> list(String articleId) {
		List<YoutubeItem> items = youtubeItemOperations.listByArticleId(articleId);
		Collections.shuffle(items);
		return items;
	}
	
	public void vote(String userId, String oneId, String anotherId, String winnerId) {

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

		Vote vote = new Vote();
		vote.setUserId(userId);
		vote.setOneItemId(oneId);
		vote.setAnotherItemId(anotherId);
		vote.setWinnerItemId(winnerId);
		voteHistoryOperations.create(vote);
		
		long voteCount = voteHistoryOperations.countByUserId(userId);
		if (voteCount % 10 == 0) {
			ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(userId);
			if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
				Connection connection = connectionRepository.getPrimaryConnection(Facebook.class);
				Object api = connection.getApi();
				String msg = MessageFormat.format("{0} voted " + voteCount + " times!", connection.getDisplayName());
				FeedOperations feedOperations = ((Facebook)api).feedOperations();
				feedOperations.postLink(msg, new FacebookLink(homeUrl, applicationName, applicationCaption, applicationDescription));

			} else if (connectionRepository.findPrimaryConnection(Twitter.class) != null) {
			}
		}
	}
}
