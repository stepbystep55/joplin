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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ippoippo.joplin.dto.Article;
import com.ippoippo.joplin.dto.UserDisplay;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.exception.IllegalRequestException;
import com.ippoippo.joplin.jdbc.mapper.UserMasterMapper;
import com.ippoippo.joplin.mongo.operations.ArticleOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;
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
	ArticleOperations articleOperations;

	@Inject
	YoutubeItemOperations youtubeItemOperations;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	@Transactional(rollbackForClassName="java.leng.Exception")
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public ModelAndView top(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String userId = userCookieForTemporaryGenerator.getUserId(request);
			if (userId == null) throw new NotSigninException();

			if (userMasterMapper.getById(userId) == null) throw new NotSigninException();

			String articleId = null;
			List<Article> articles = articleOperations.getActive();
			if (articles != null && articles.size() > 0) {
				articleId = articles.get(0).getId();
			} else {
				//TODO
			}

			userCookieForTemporaryGenerator.addUserId(response, userId); // renew cookie for extending maxage
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/hon/" + articleId + "/match");
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
	@RequestMapping(value = "/hon/{articleId}/match", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView match(@PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("match");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hon/{articleId}/vs", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView vs(@PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);

		List<YoutubeItem> items = this.newVs(articleId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("firstItem", items.get(0));
		modelAndView.addObject("secondItem", items.get(1));
		modelAndView.setViewName("vs");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hon/{articleId}/vote", method = RequestMethod.POST)
	public ModelAndView vote(
			@PathVariable String articleId
			, @RequestParam("firstItemId") String firstItemId
			, @RequestParam("secondItemId") String secondItemId
			, @RequestParam("winnerItemId") String winnerItemId
			) throws IllegalRequestException {

		validateAccess(articleId);

		List<String> ids = new ArrayList<String>(2);
		ids.add(firstItemId);
		ids.add(secondItemId);
		List<YoutubeItem> winnerAndLoser = youtubeItemOperations.listByIds(ids);
		YoutubeItem winnerItem = null;
		YoutubeItem loserItem = null;
		for (YoutubeItem item : winnerAndLoser) {
			if (item.getId().equals(winnerItemId)) {
				winnerItem = item;
			} else {
				loserItem = item;
			}
		}
		winnerItem.calcRateVaried(true, loserItem.getRate());
		loserItem.calcRateVaried(false, winnerItem.getRate());

		youtubeItemOperations.updateRate(winnerItem.getId(), winnerItem.getRateVaried());
		youtubeItemOperations.updateRate(loserItem.getId(), loserItem.getRateVaried());
		
		List<YoutubeItem> items = this.newVs(articleId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("firstItem", items.get(0));
		modelAndView.addObject("secondItem", items.get(1));
		modelAndView.setViewName("vs");
		return modelAndView;
	}
	
	private List<YoutubeItem> newVs(String articleId) {

		List<YoutubeItem> items = youtubeItemOperations.listByArticleId(articleId);
		if (items.size() <= 1) {
			// TODO
		}
		Collections.shuffle(items);

		return items.subList(0, 2);
	}

	private void validateAccess(String articleId) throws IllegalRequestException {
		
		Article article = articleOperations.getById(articleId);
		if (article == null) throw new IllegalRequestException();
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hon/{articleId}/rank", method = RequestMethod.GET)
	public ModelAndView rank(@PathVariable String articleId) {
		
		Article article = articleOperations.getById(articleId);
		List<YoutubeItem> items = youtubeItemOperations.listTopRate(articleId, 10);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("article", article);
		modelAndView.addObject("items", items);
		modelAndView.setViewName("rank");
		return modelAndView;
	}
}
