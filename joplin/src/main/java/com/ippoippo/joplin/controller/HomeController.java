package com.ippoippo.joplin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ippoippo.joplin.dto.UserDisplay;
import com.ippoippo.joplin.util.UserCookieForTemporaryGenerator;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	UserCookieForTemporaryGenerator userCookieForTemporaryGenerator;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String userId = userCookieForTemporaryGenerator.getUserId(request);
			if (userId == null) throw new NotSigninException();

			ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(userId);
			MultiValueMap<String, Connection<?>> connections = connectionRepository.findAllConnections();
			if (connections.isEmpty()) throw new NotSigninException();

			List<UserDisplay> userDisplays = new ArrayList<UserDisplay>();
			for (String providerName: connections.keySet()) {
				for (Connection<?> connection : connections.get(providerName)) {
					UserDisplay userDisplay = new UserDisplay();
					userDisplay.setProviderName(providerName);
					userDisplay.setName(connection.getDisplayName());
					userDisplay.setImageUrl(connection.getImageUrl());
					userDisplays.add(userDisplay);
				}
			}
			if (userDisplays.isEmpty()) throw new NotSigninException(); // because connections.isEmpty() doesn't work well

			userCookieForTemporaryGenerator.addUserId(response, userId); // renew cookie for extending maxage
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("userDisplays", userDisplays);
			modelAndView.setViewName("top");
			return modelAndView;

		} catch (NotSigninException e) {
			userCookieForTemporaryGenerator.removeUserId(response);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("signin");
			return modelAndView;
			
		}
	}

	private class NotSigninException extends Exception {
		public NotSigninException() { }
	}
}
