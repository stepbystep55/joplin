package com.ippoippo.joplin.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ippoippo.joplin.dto.Article;
import com.ippoippo.joplin.dto.Match;
import com.ippoippo.joplin.dto.UserDisplay;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.jdbc.mapper.ArticleMapper;
import com.ippoippo.joplin.jdbc.mapper.UserMasterMapper;
import com.ippoippo.joplin.jdbc.mapper.YoutubeItemMapper;
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
	UserMasterMapper userMasterMapper;

	@Inject
	ArticleMapper articleMapper;

	@Inject
	YoutubeItemMapper youtubeItemMapper;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	@Transactional(rollbackForClassName="java.leng.Exception")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String userId = userCookieForTemporaryGenerator.getUserId(request);
			if (userId == null) throw new NotSigninException();

			if (userMasterMapper.getById(userId) == null) throw new NotSigninException();

			userCookieForTemporaryGenerator.addUserId(response, userId); // renew cookie for extending maxage
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("forward:/top");
			return modelAndView;

		} catch (NotSigninException e) {
			userCookieForTemporaryGenerator.removeUserId(response);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("login");
			return modelAndView;
			
		}
	}
	
	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/header", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView header(HttpServletRequest request) {

		try {
			String userId = userCookieForTemporaryGenerator.getUserId(request);
			if (userId == null) throw new NotSigninException();
	
			ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(userId);
	
			UserDisplay userDisplay = new UserDisplay();
			boolean success = userDisplay.setWithConnectionRepository(connectionRepository);
			if (!success) throw new NotSigninException();
	
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("userDisplay", userDisplay);
			modelAndView.setViewName("header");
			return modelAndView;

		} catch (NotSigninException e) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("header");
			return modelAndView;
		}
	}

	private class NotSigninException extends Exception {
		public NotSigninException() { }
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/top", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView top() {

		String articleId = null;
		List<Article> articles = articleMapper.getActive();
		if (articles != null && articles.size() > 0) {
			articleId = articles.get(0).getId();
		} else {
			//TODO
		}

		Match match = this.newMatch(articleId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("match", match);
		modelAndView.setViewName("top");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/vs", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView vs(Match match) {
		
		youtubeItemMapper.updateRate(match);
		
		match = this.newMatch(match.getArticleId());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("match", match);
		modelAndView.setViewName("top");
		return modelAndView;
	}
	
	private Match newMatch(String articleId) {
		
		List<YoutubeItem> items = youtubeItemMapper.listByArticleId(articleId);
		if (items.size() <= 1) {
			// TODO
		}
		List<YoutubeItem> copyItems = new ArrayList<YoutubeItem>(items.size());
		Collections.copy(copyItems, items);
		Collections.shuffle(copyItems);

		Match match = new Match();
		match.setArticleId(articleId);
		match.setFirstItem(copyItems.get(0));
		match.setSecondItem(copyItems.get(1));
		return match;
	}
}
