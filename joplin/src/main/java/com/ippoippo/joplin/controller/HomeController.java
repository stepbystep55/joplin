package com.ippoippo.joplin.controller;

import java.io.IOException;
import java.net.URL;

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

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;
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

	public static final String YOUTUBE_GDATA_SERVER = "http://gdata.youtube.com";
	public static final String VIDEOS_FEED = YOUTUBE_GDATA_SERVER + "/feeds/api/videos";

	private void searchFeed() throws IOException, ServiceException {
		YouTubeService myService = new YouTubeService("gdataSample-YouTube-1");
		
		YouTubeQuery query = new YouTubeQuery(new URL(VIDEOS_FEED));
		// order results by the number of views (most viewed first)
		query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);

		// do not exclude restricted content from the search results 
		// (by default, it is excluded) 
		query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);

		String searchTerms = "adele";

		query.setFullTextQuery(searchTerms);

		logger.info("Running Search for '" + searchTerms + "'");
		VideoFeed videoFeed = myService.query(query, VideoFeed.class);
		for (VideoEntry ve : videoFeed.getEntries()) {
			logger.info("title="+ve.getTitle()+", summary="+ve.getSummary());
		}
	}

	private class NotSigninException extends Exception {
		public NotSigninException() { }
	}
}
