package com.ippoippo.joplin.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FriendOperations;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Service;

import com.ippoippo.joplin.dto.Friends;
import com.ippoippo.joplin.mongo.operations.FriendListOperations;

@Service
public class FriendService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	FriendListOperations friendListOperations;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	@Async
	public void updateFriends(String userId, Connection<?> connection) {

		String providerId = connection.createData().getProviderId();

		// delete for renewal friend list
		Friends friendList = friendListOperations.getByUserId(userId);
		if (friendList != null) friendListOperations.delete(friendList.getId());

		// register friend list
		Object api = connection.getApi();
		if (api instanceof Facebook) {
			FriendOperations friendOperations = ((Facebook)api).friendOperations();
			List<Reference> fbFriendList = friendOperations.getFriends();

			friendList = new Friends();
			friendList.setProviderId(providerId);
			friendList.setUserId(userId);
			Set<String> friendProviderIds = new HashSet<String>(fbFriendList.size());
			for (Reference ref : fbFriendList) friendProviderIds.add(ref.getId());
			Set<String> friendIds = usersConnectionRepository.findUserIdsConnectedTo(providerId, friendProviderIds);
			friendList.setFriendIds(friendIds);

			friendListOperations.create(friendList);
		}
		logger.info("friends updated: size=" + friendList.getFriendIds().size());
	}
}
