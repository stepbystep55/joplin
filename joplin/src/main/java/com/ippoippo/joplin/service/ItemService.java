package com.ippoippo.joplin.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.ippoippo.joplin.dto.Contribution;
import com.ippoippo.joplin.dto.Vote;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.mongo.operations.ContributionOperations;
import com.ippoippo.joplin.mongo.operations.VoteHistoryOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;
import com.ippoippo.joplin.util.Utils;

@Service
public class ItemService {
	
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
	YoutubeItemOperations youtubeItemOperations;

	@Inject
	VoteHistoryOperations voteHistoryOperations;

	@Inject
	ContributionOperations contributionOperations;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	// 内部呼出メソッドの@Cacheableが効かないため、分離する
	//public List<YoutubeItem> newMatch(String articleId) {
		//List<YoutubeItem> items = this.list(articleId);
	public List<YoutubeItem> newMatch(List<YoutubeItem> items) {

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

	@Cacheable("itemList")
	public List<YoutubeItem> list(String articleId) {
		List<YoutubeItem> items = youtubeItemOperations.listByArticleId(articleId);
		Collections.shuffle(items);
		logger.info("getting item list: size=" + items.size());
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
		if (voteCount % 25 == 0) {
			ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(userId);
			if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
				Connection connection = connectionRepository.getPrimaryConnection(Facebook.class);
				Object api = connection.getApi();
				String msg = MessageFormat.format("{0} made " + voteCount + " votes!", connection.getDisplayName());
				FeedOperations feedOperations = ((Facebook)api).feedOperations();
				feedOperations.postLink(msg, new FacebookLink(homeUrl, applicationName, applicationCaption, applicationDescription));

			} else if (connectionRepository.findPrimaryConnection(Twitter.class) != null) {
			}
		}
	}
	
	public void contribute(String articleId, String videoId, String userId, boolean canShare) {

		// register video
		if (youtubeItemOperations.countByArticleIdAndVideoId(articleId, videoId) > 0) {
			logger.info("The video for articldId="+articleId+", videoId="+videoId+" already exists.");
		} else {
			YoutubeItem item = new YoutubeItem();
			item.setArticleId(articleId);
			item.setVideoId(videoId);
			youtubeItemOperations.create(item);
		}
		// register contribution
		Contribution contribution = new Contribution();
		contribution.setArticleId(articleId);
		contribution.setUserId(userId);
		contribution.setVideoId(videoId);
		contributionOperations.create(contribution);
		// share about the contribution
		if (canShare) {
			ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(userId);
			if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
				Connection connection = connectionRepository.getPrimaryConnection(Facebook.class);
				Object api = connection.getApi();
				String msg = MessageFormat.format("{0} posted a video!", connection.getDisplayName());
				FeedOperations feedOperations = ((Facebook)api).feedOperations();
				feedOperations.postLink(msg, new FacebookLink(homeUrl, applicationName, applicationCaption, applicationDescription));

			} else if (connectionRepository.findPrimaryConnection(Twitter.class) != null) {
			}
		}
	}
}
