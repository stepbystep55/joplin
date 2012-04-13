package com.ippoippo.joplin.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ippoippo.joplin.dto.Article;
import com.ippoippo.joplin.dto.Contribution;
import com.ippoippo.joplin.dto.UserDisplay;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.dto.YoutubeSearchForm;
import com.ippoippo.joplin.exception.IllegalRequestException;
import com.ippoippo.joplin.jdbc.mapper.UserMasterMapper;
import com.ippoippo.joplin.mongo.operations.ArticleOperations;
import com.ippoippo.joplin.mongo.operations.ContributionOperations;
import com.ippoippo.joplin.mongo.operations.VoteHistoryOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;
import com.ippoippo.joplin.service.YoutubeSearchService;
import com.ippoippo.joplin.util.UserCookieForTemporaryGenerator;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	YoutubeSearchService youtubeSearchService;

	@Inject
	UserCookieForTemporaryGenerator userCookieForTemporaryGenerator;

	@Inject
	UserMasterMapper userMasterMapper;

	@Inject
	ArticleOperations articleOperations;

	@Inject
	YoutubeItemOperations youtubeItemOperations;

	@Inject
	ContributionOperations contributionOperations;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	@Transactional(rollbackForClassName="java.leng.Exception")
	@RequestMapping(value = "/", method = RequestMethod.GET)
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
				articleId = Article.ID_FOR_NO_ARTICLE;
			}

			userCookieForTemporaryGenerator.addUserId(response, userId); // renew cookie for extending maxage
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/hon/" + articleId + "/battle");
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
	@RequestMapping(value = "/hon/{articleId}/battle", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView battle(@PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);

		Article article = (articleId.equals(Article.ID_FOR_NO_ARTICLE)) ? new Article() : articleOperations.getById(articleId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("article", article);
		modelAndView.setViewName("battle");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hon/{articleId}/vs", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView vs(@PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);
		if (articleId.equals(Article.ID_FOR_NO_ARTICLE)) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("noVs");
			return modelAndView;
		}

		List<YoutubeItem> items = youtubeSearchService.newMatch(articleId);

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
			, HttpServletRequest request
			) throws IllegalRequestException {

		validateAccess(articleId);

		String userId = userCookieForTemporaryGenerator.getUserId(request);

		youtubeSearchService.vote(userId, firstItemId, secondItemId, winnerItemId);

		List<YoutubeItem> items = youtubeSearchService.newMatch(articleId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("firstItem", items.get(0));
		modelAndView.addObject("secondItem", items.get(1));
		modelAndView.setViewName("vs");
		return modelAndView;
	}

	private void validateAccess(String articleId) throws IllegalRequestException {
		if (articleId.equals(Article.ID_FOR_NO_ARTICLE)) return;
		Article article = articleOperations.getById(articleId);
		if (article == null) throw new IllegalRequestException();
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hon/{articleId}/rank", method = RequestMethod.GET)
	public ModelAndView rank(@PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);

		Article article = (articleId.equals(Article.ID_FOR_NO_ARTICLE)) ? new Article() : articleOperations.getById(articleId);
		List<YoutubeItem> items = youtubeItemOperations.listTopRate(articleId, 10);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("article", article);
		modelAndView.addObject("items", items);
		modelAndView.setViewName("rank");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hon/{articleId}/item", method = RequestMethod.POST)
	public ModelAndView item(HttpServletRequest request, @PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);

		String userId = userCookieForTemporaryGenerator.getUserId(request);

		Contribution contribution = contributionOperations.getByArticleIdAndUserId(articleId, userId);
		if (articleId.equals(Article.ID_FOR_NO_ARTICLE) || contribution != null) {
			// already registered (user can post only one video for each article.)
			ModelAndView modelAndView = new ModelAndView();
			if (contribution != null) modelAndView.addObject("videoId", contribution.getVideoId());
			modelAndView.setViewName("/yourItem");
			return modelAndView;
		}

		YoutubeSearchForm form = new YoutubeSearchForm();
		form.setArticleId(articleId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("youtubeSearchForm", form);
		modelAndView.setViewName("/item");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hon/{articleId}/searchItem", method = RequestMethod.POST)
	public ModelAndView searchItem(
			@PathVariable String articleId
			, @RequestParam("command") String command
			, @Valid YoutubeSearchForm youtubeSearchForm
			, BindingResult result
			) throws IllegalRequestException, IOException {
		
		validateAccess(articleId);

		if (result.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("youtubeSearchForm", youtubeSearchForm);
			modelAndView.setViewName("/item");
			return modelAndView;
		}
		if (command.equals("prev")) {
			youtubeSearchForm.prev();
		} else if (command.equals("next")) {
			youtubeSearchForm.next();
		}
		List<YoutubeItem> items = youtubeSearchService.search(
				articleId
				,youtubeSearchForm.getSearchText()
				, youtubeSearchForm.getStartIndex()
				, youtubeSearchForm.getListSize());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("youtubeSearchForm", youtubeSearchForm);
		modelAndView.addObject("items", items);
		modelAndView.setViewName("/item");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hon/{articleId}/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(
			HttpServletRequest request
			, @PathVariable String articleId
			, @RequestParam("videoId") String videoId) throws IllegalRequestException {

		validateAccess(articleId);

		// register video
		if (youtubeItemOperations.countByArticleIdAndVideoId(articleId, videoId) > 0) {
			logger.info("The video for articldId="+articleId+", videoId="+videoId+" already exists.");
		} else {
			YoutubeItem item = new YoutubeItem();
			item.setArticleId(articleId);
			item.setVideoId(videoId);
			youtubeItemOperations.create(item);
		}
		// register contribution
		String userId = userCookieForTemporaryGenerator.getUserId(request);
		Contribution contribution = new Contribution();
		contribution.setArticleId(articleId);
		contribution.setUserId(userId);
		contribution.setVideoId(videoId);
		contributionOperations.create(contribution);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("updated", true);
		modelAndView.setViewName("forward:battle");
		return modelAndView;
	}
}
