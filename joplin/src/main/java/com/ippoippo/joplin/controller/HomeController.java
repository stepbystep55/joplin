package com.ippoippo.joplin.controller;

import java.io.IOException;
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

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.json.JsonCParser;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.Key;
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

			UserDisplay userDisplay = new UserDisplay();
			boolean success = userDisplay.setWithConnectionRepository(connectionRepository);
			if (!success) throw new NotSigninException();
			try {
				this.searchFeed();
			} catch (Exception e) {
				logger.error(e.toString(), e);
			}

			userCookieForTemporaryGenerator.addUserId(response, userId); // renew cookie for extending maxage
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("userDisplay", userDisplay);
			modelAndView.setViewName("top");
			return modelAndView;

		} catch (NotSigninException e) {
			userCookieForTemporaryGenerator.removeUserId(response);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("login");
			return modelAndView;
			
		}
	}

	public static class VideoFeed {
		@Key List<Video> items;
	}

	public static class Video {
		@Key String title;
		@Key String description;
		@Key Player player;
	}

	public static class Player {
		@Key("default") String defaultUrl;
	}

	public static class YouTubeUrl extends GenericUrl {
		@Key final String alt = "jsonc";
		@Key String author;
		@Key("max-results") Integer maxResults;

		YouTubeUrl(String url) {
			super(url);
		}
	}

	@Inject
	private HttpRequestFactory gdataRequestFactory;

	private void searchFeed() throws IOException {
		// build the YouTube URL
		YouTubeUrl url = new YouTubeUrl("https://gdata.youtube.com/feeds/api/videos");
		url.author = "searchstories";
		url.maxResults = 2;
		// build the HTTP GET request
		HttpRequest request = gdataRequestFactory.buildGetRequest(url);
		JsonCParser parser = new JsonCParser(new JacksonFactory());
		request.addParser(parser);
		logger.info("req url="+request.getUrl());
		logger.info("req method="+request.getMethod());
		// execute the request and the parse video feed
		VideoFeed feed = request.execute().parseAs(VideoFeed.class);
		for (Video video : feed.items) {
			logger.info("Video title: " + video.title);
			logger.info("Description: " + video.description);
			logger.info("Play URL: " + video.player.defaultUrl);
		}
	}

	private class NotSigninException extends Exception {
		public NotSigninException() { }
	}
}
