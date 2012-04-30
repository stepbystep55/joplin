package com.ippoippo.joplin.social;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FriendOperations;
import org.springframework.social.facebook.api.Reference;
import org.springframework.web.context.request.NativeWebRequest;

import com.ippoippo.joplin.dto.Friends;
import com.ippoippo.joplin.mongo.operations.FriendListOperations;
import com.ippoippo.joplin.util.UserCookieForTemporaryGenerator;

/**
 * 
 */
public final class SimpleSignInAdapter implements SignInAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private UserCookieForTemporaryGenerator userCookieGenerator;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	@Inject
	private FriendListOperations friendListOperations;

	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		logger.info("Signin with userId=" + userId);
		
		HttpServletResponse nativeResponse = (HttpServletResponse)request.getNativeResponse();
		userCookieGenerator.addUserId(nativeResponse, userId);

		String providerId = connection.createData().getProviderId();

		// delete for renewal friend list
		Friends friendList = friendListOperations.getByProviderIdAndUserId(providerId, userId);
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
		return null;
	}

}