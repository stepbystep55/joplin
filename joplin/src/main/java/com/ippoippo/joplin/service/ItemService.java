package com.ippoippo.joplin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Service;

import com.ippoippo.joplin.dto.ItemListForm;
import com.ippoippo.joplin.dto.Vote;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.jdbc.mapper.UserMasterMapper;
import com.ippoippo.joplin.mongo.operations.ContributionOperations;
import com.ippoippo.joplin.mongo.operations.FriendListOperations;
import com.ippoippo.joplin.mongo.operations.VoteHistoryOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;
import com.ippoippo.joplin.util.Utils;

@Service
public class ItemService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	YoutubeItemOperations youtubeItemOperations;

	@Inject
	VoteHistoryOperations voteHistoryOperations;

	@Inject
	ContributionOperations contributionOperations;

	@Inject
	FriendListOperations friendListOperations;

	@Inject
	UserMasterMapper userMasterMapper;

	/*
	@Inject
	UserconnectionMapper userconnectionMapper;
	*/

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	public void add(YoutubeItem item) {

		if (youtubeItemOperations.countByArticleIdAndVideoId(item.getArticleId(), item.getVideoId()) > 0) {
			logger.info("This item="+item+" already exists.");
		} else {
			youtubeItemOperations.create(item);
		}
	}

	public List<YoutubeItem> list(String articleId, ItemListForm itemListForm) {
		itemListForm.update();
		return youtubeItemOperations.listByArticleId(articleId, itemListForm.getStartIndex(), itemListForm.getListSize());
	}

	public void delete(String itemId) {
		youtubeItemOperations.delete(itemId);
	}

	// 内部呼出メソッドの@Cacheableが効かないため分離する
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
	
	public void vote(String articleId, String userId, String winnerId, String loserId) {

		List<YoutubeItem> twoItems = youtubeItemOperations.listByIds(Arrays.asList(winnerId, loserId));

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
		vote.setArticleId(articleId);
		vote.setUserId(userId);
		vote.setOneItemId(winnerId);
		vote.setAnotherItemId(loserId);
		vote.setWinnerItemId(winnerId);
		voteHistoryOperations.create(vote);
	}
	
	public long countVote(String articleId, String userId) {
		return voteHistoryOperations.countByArticleIdAndUserId(articleId, userId);
	}

	@Cacheable("topItemList")
	public List<YoutubeItem> listTopRate(String articleId, int rankListSize) {
		return youtubeItemOperations.listTopRate(articleId, rankListSize);
	}

	/*
	public List<UserItem> listFriends(String articleId, String userId, String providerId) {
		Friends friends = friendListOperations.getByProviderIdAndUserId(providerId, userId);
		List<UserItem> userItemList = new ArrayList<UserItem>(friends.getFriendIds().size());
		List<Userconnection> userconnectionList = userconnectionMapper.listInUserIds(friends.getFriendIds());
		return userItemList;
	}
	*/
}
