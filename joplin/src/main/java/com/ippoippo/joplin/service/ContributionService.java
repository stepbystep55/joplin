package com.ippoippo.joplin.service;

import java.text.MessageFormat;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.stereotype.Service;

import com.ippoippo.joplin.dto.Article;
import com.ippoippo.joplin.dto.Contribution;
import com.ippoippo.joplin.dto.Friends;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.jdbc.mapper.UserMasterMapper;
import com.ippoippo.joplin.mongo.operations.ContributionOperations;
import com.ippoippo.joplin.mongo.operations.FriendListOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;

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
	ArticleService articleService;

	@Inject
	ContributionOperations contributionOperations;

	@Inject
	FriendListOperations friendListOperations;

	@Inject
	YoutubeItemOperations youtubeItemOperations;

	@Inject
	UserMasterMapper userMasterMapper;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	public Contribution get(String articleId, String userId) {
		return contributionOperations.getByArticleIdAndUserId(articleId, userId);
	}

	@CacheEvict(value="itemList", allEntries=true)
	public void contribute(String articleId, String userId, YoutubeItem item, boolean canShare) {

		// retrieve connection
		Connection connection = null;
		ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(userId);
		if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
			connection = connectionRepository.getPrimaryConnection(Facebook.class);

		//} else if (connectionRepository.findPrimaryConnection(Twitter.class) != null) {
		}
		if (connection == null) {
			String msg = "No connection found for userId=" + userId;
			logger.error(msg);
			throw new RuntimeException(msg);
		}

		// register contribution
		Contribution contribution = new Contribution();
		contribution.setArticleId(articleId);
		contribution.setUserId(userId);
		contribution.setDisplayName(connection.getDisplayName());
		contribution.setItem(item);
		contributionOperations.create(contribution);

		// share about the contribution
		if (canShare) {
			Object api = connection.getApi();
			if (api instanceof Facebook) {
				String msg = MessageFormat.format("{0} posted a video!", connection.getDisplayName());
				FeedOperations feedOperations = ((Facebook)api).feedOperations();
				feedOperations.postLink(msg, new FacebookLink(homeUrl, applicationName, applicationCaption, applicationDescription));

			//} else if (api instanceof Twitter) {
			}
		}
	}

	@Scheduled(fixedDelay=600000)
	public void updateRank() {
		Article article = articleService.getActive();
		List<Contribution> contributions = contributionOperations.listByArticleId(article.getId());
		for (Contribution contribution : contributions) {
			long rank = youtubeItemOperations.rank(contribution.getItem().getId());
			contributionOperations.updateRank(contribution.getId(), rank);
		}
		logger.info("contribution rank updated: size="+contributions.size());
	}

	public List<Contribution> listFriendContributions(String articleId, String userId) {
		Friends friends = friendListOperations.getByUserId(userId);
		return contributionOperations.listByArticleIdAndUserIds(articleId, friends.getFriendIds());
	}
}
