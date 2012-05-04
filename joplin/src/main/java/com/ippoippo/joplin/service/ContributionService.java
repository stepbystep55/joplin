package com.ippoippo.joplin.service;

import java.text.MessageFormat;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.ippoippo.joplin.dto.Contribution;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.jdbc.mapper.UserMasterMapper;
import com.ippoippo.joplin.mongo.operations.ContributionOperations;
import com.ippoippo.joplin.mongo.operations.FriendListOperations;

@Service
public class ContributionService {
	
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

	public Contribution get(String articleId, String userId) {
		return contributionOperations.getByArticleIdAndUserId(articleId, userId);
	}

	@CacheEvict(value="itemList", allEntries=true)
	public void contribute(String articleId, String userId, YoutubeItem item, boolean canShare) {

		// register contribution
		Contribution contribution = new Contribution();
		contribution.setArticleId(articleId);
		contribution.setUserId(userId);
		contribution.setItem(item);
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

	/*
	public List<UserItem> listFriends(String articleId, String userId, String providerId) {
		Friends friends = friendListOperations.getByProviderIdAndUserId(providerId, userId);
		List<UserItem> userItemList = new ArrayList<UserItem>(friends.getFriendIds().size());
		List<Userconnection> userconnectionList = userconnectionMapper.listInUserIds(friends.getFriendIds());
		return userItemList;
	}
	*/
}
