package com.ippoippo.joplin.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import com.ippoippo.joplin.mongo.operations.ContributionOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;
import com.ippoippo.joplin.service.ArticleService;
import com.ippoippo.joplin.service.ItemService;
import com.ippoippo.joplin.service.UserService;
import com.ippoippo.joplin.service.YoutubeSearchService;
import com.ippoippo.joplin.util.UserCookieForTemporaryGenerator;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${facebook.clientId}")
	private String facebookClientId;
	
	@Value("${home.url}")
	private String homeUrl;
	
	@Value("${rank.list.size}")
	private int rankListSize;

	@Value("${vote.mincount.required}")
	private int voteMinCountRequired;

	@Inject
	YoutubeSearchService youtubeSearchService;

	@Inject
	ItemService itemService;

	@Inject
	ArticleService articleService;

	@Inject
	UserService userService;

	@Inject
	UserCookieForTemporaryGenerator userCookieForTemporaryGenerator;

	@Inject
	YoutubeItemOperations youtubeItemOperations;

	@Inject
	ContributionOperations contributionOperations;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	@Transactional(rollbackForClassName="java.leng.Exception")
	@RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView top(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String userId = userCookieForTemporaryGenerator.getUserId(request);
			if (userId == null) throw new NotSigninException();

			if (userService.getById(userId) == null) throw new NotSigninException();

			Article article = articleService.getActive();

			userCookieForTemporaryGenerator.addUserId(response, userId); // renew cookie for extending maxage
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/hn/" + article.getId() + "/battle");
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
	@RequestMapping(value = "/hn/{articleId}/battle", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView battle(@PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);

		Article article = (articleId.equals(Article.ID_FOR_NO_ARTICLE)) ? new Article() : articleService.getById(articleId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("article", article);
		modelAndView.addObject("facebookClientId", facebookClientId);
		modelAndView.addObject("homeUrl", homeUrl);
		modelAndView.setViewName("battle");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hn/{articleId}/vs", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView vs(@PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);
		if (articleId.equals(Article.ID_FOR_NO_ARTICLE)) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("noVs");
			return modelAndView;
		}

		List<YoutubeItem> items = itemService.list(articleId);
		List<YoutubeItem> match = itemService.newMatch(items);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("firstItem", match.get(0));
		modelAndView.addObject("secondItem", match.get(1));
		modelAndView.setViewName("vs");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hn/{articleId}/vote", method = RequestMethod.POST)
	public ModelAndView vote(
			@PathVariable String articleId
			, @RequestParam("winnerItemId") String winnerItemId
			, @RequestParam("loserItemId") String loserItemId
			, HttpServletRequest request
			) throws IllegalRequestException {

		validateAccess(articleId);

		String userId = userCookieForTemporaryGenerator.getUserId(request);

		itemService.vote(articleId, userId, winnerItemId, loserItemId);

		List<YoutubeItem> items = itemService.list(articleId);
		List<YoutubeItem> match = itemService.newMatch(items);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("firstItem", match.get(0));
		modelAndView.addObject("secondItem", match.get(1));
		modelAndView.setViewName("vs");
		return modelAndView;
	}

//	private void validateAccess(String articleId, String userId) throws IllegalRequestException {
//		if (userMasterMapper.getById(userId) == null) throw new IllegalRequestException();
	private void validateAccess(String articleId) throws IllegalRequestException {

		if (articleId.equals(Article.ID_FOR_NO_ARTICLE)) return;
		Article article = articleService.getById(articleId);
		if (article == null) throw new IllegalRequestException();
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hn/{articleId}/rank", method = RequestMethod.GET)
	public ModelAndView rank(HttpServletRequest request, @PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);

		String userId = userCookieForTemporaryGenerator.getUserId(request);

		Article article = (articleId.equals(Article.ID_FOR_NO_ARTICLE)) ? new Article() : articleService.getById(articleId);
		List<YoutubeItem> items = itemService.listTopRate(articleId, rankListSize);
		long voteCount = itemService.countVote(articleId, userId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("article", article);
		modelAndView.addObject("items", items);
		if (voteCount >= voteMinCountRequired) {
			modelAndView.addObject("voteMinCountReached", true);
		} else {
			modelAndView.addObject("voteMinCountReached", false);
		}
		modelAndView.setViewName("rank");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hn/{articleId}/friends", method = RequestMethod.GET)
	public ModelAndView friends(HttpServletRequest request, @PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);

		String userId = userCookieForTemporaryGenerator.getUserId(request);

		Article article = (articleId.equals(Article.ID_FOR_NO_ARTICLE)) ? new Article() : articleService.getById(articleId);
		List<YoutubeItem> items = itemService.listTopRate(articleId, rankListSize);
		long voteCount = itemService.countVote(articleId, userId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("article", article);
		modelAndView.addObject("items", items);
		if (voteCount >= voteMinCountRequired) {
			modelAndView.addObject("voteMinCountReached", true);
		} else {
			modelAndView.addObject("voteMinCountReached", false);
		}
		modelAndView.setViewName("rank");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hn/{articleId}/item", method = RequestMethod.POST)
	public ModelAndView item(HttpServletRequest request, @PathVariable String articleId) throws IllegalRequestException {

		validateAccess(articleId);

		String userId = userCookieForTemporaryGenerator.getUserId(request);

		Contribution contribution = contributionOperations.getByArticleIdAndUserId(articleId, userId);
		if (articleId.equals(Article.ID_FOR_NO_ARTICLE) || contribution != null) {
			// already registered (user can post only one item for each article.)
			ModelAndView modelAndView = new ModelAndView();
			if (contribution != null) {
				modelAndView.addObject("contribution", contribution);
				//long rank = youtubeItemOperations.rankForVideoId(articleId, contribution.getVideoId());
				//modelAndView.addObject("rank", rank);
			}
			modelAndView.setViewName("/yourItem");
			return modelAndView;
		}

		YoutubeSearchForm form = new YoutubeSearchForm();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("youtubeSearchForm", form);
		modelAndView.setViewName("/item");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hn/{articleId}/searchItem", method = RequestMethod.POST)
	public ModelAndView searchItem(
			@PathVariable String articleId
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
		youtubeSearchForm.update();
		List<YoutubeItem> items = youtubeSearchService.search(articleId, youtubeSearchForm);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("youtubeSearchForm", youtubeSearchForm);
		modelAndView.addObject("items", items);
		if (items == null || items.size() == 0) modelAndView.addObject("message", "No result");
		modelAndView.setViewName("/item");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/hn/{articleId}/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(
			HttpServletRequest request
			, @PathVariable String articleId
			, @Valid YoutubeItem item
			, BindingResult result
			) throws IllegalRequestException {

		boolean canShare = Boolean.parseBoolean(request.getParameter("canShare"));
		validateAccess(articleId);

		if (result.hasErrors()) throw new IllegalRequestException(""+result.getAllErrors());

		String userId = userCookieForTemporaryGenerator.getUserId(request);
		itemService.contribute(articleId, userId, item, canShare);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("updated", true);
		modelAndView.setViewName("forward:battle");
		return modelAndView;
	}
}
