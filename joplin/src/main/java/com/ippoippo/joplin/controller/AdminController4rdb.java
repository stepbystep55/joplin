package com.ippoippo.joplin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.client.googleapis.json.JsonCParser;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.ippoippo.joplin.dto.Article;
import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.dto.YoutubeSearchForm;
import com.ippoippo.joplin.exception.IllegalRequestException;
import com.ippoippo.joplin.jdbc.mapper.ArticleMapper;
import com.ippoippo.joplin.jdbc.mapper.YoutubeItemMapper;
import com.ippoippo.joplin.util.StringUtils;
import com.ippoippo.joplin.youtube.Video;
import com.ippoippo.joplin.youtube.VideoFeed;
import com.ippoippo.joplin.youtube.YouTubeSearchUrl;

/**
 * Handles requests for articles.
 */
@Controller
@RequestMapping("/adminforrb")
public class AdminController4rdb {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${admin.password.md5}")
	private String adminPasswordMD5;

	public static final String SESSION_KEY_AUTH = "loginAsAdmin";

	@Inject
	ArticleMapper articleMapper;

	@Inject
	YoutubeItemMapper youtubeItemMapper;

	@Inject
	private HttpRequestFactory gdataRequestFactory;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView top() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/login");
		return modelAndView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("password") String password, HttpServletRequest request) {
		
		if (StringUtils.hashMD5(password).equals(adminPasswordMD5)) {
			request.getSession().setAttribute(SESSION_KEY_AUTH, true);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:article/list");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("failed", true);
		modelAndView.setViewName("admin/login");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/article/list", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView list() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("articles", articleMapper.listLatest(0, 10));
		modelAndView.setViewName("admin/article/list");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/article/new", method = RequestMethod.POST)
	public ModelAndView create() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("article", new Article());
		modelAndView.setViewName("admin/article/new");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/article/create", method = RequestMethod.POST)
	public ModelAndView create(@Valid Article article, BindingResult result) {

		if (result.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("article", article);
			modelAndView.setViewName("admin/article/new");
			return modelAndView;
		}

		Integer newId = articleMapper.newId();
		article.setId(newId.toString());
		articleMapper.create(article);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("created", true);
		modelAndView.setViewName("redirect:"+newId+"/edit");
		return modelAndView;
	}

	private void validateAccess(String articleId) throws IllegalRequestException {
		
		Article article = articleMapper.getById(articleId);
		if (article == null) throw new IllegalRequestException();
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/article/{id}/edit", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView edit(@PathVariable String id) throws IllegalRequestException {

		validateAccess(id);

		Article article = articleMapper.getById(id);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("article", article);
		modelAndView.addObject("items", youtubeItemMapper.listByArticleId(article.getId()));
		modelAndView.setViewName("admin/article/edit");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/article/{id}/update", method = RequestMethod.POST)
	public ModelAndView update(@PathVariable String id, @Valid Article article, BindingResult result) throws IllegalRequestException {

		validateAccess(id);

		if (result.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("article", article);
			modelAndView.addObject("items", youtubeItemMapper.listByArticleId(article.getId()));
			modelAndView.setViewName("admin/article/edit");
			return modelAndView;
		}

		article.setId(id);
		articleMapper.update(article);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("updated", true);
		modelAndView.setViewName("forward:edit");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/article/{id}/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView delete(@PathVariable String id) throws IllegalRequestException {
		
		validateAccess(id);

		articleMapper.delete(id);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("deleted", true);
		modelAndView.setViewName("redirect:/admin/article/list");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/article/{id}/newItem", method = RequestMethod.POST)
	public ModelAndView createItem(@PathVariable String id) throws IllegalRequestException, IOException {

		validateAccess(id);

		YoutubeSearchForm form = new YoutubeSearchForm();
		form.setArticleId(id);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("youtubeSearchForm", form);
		modelAndView.setViewName("admin/article/newItem");
		return modelAndView;
	}
	
	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/article/{id}/searchItem", method = RequestMethod.POST)
	public ModelAndView searchItem(
			@PathVariable String id
			, @RequestParam("command") String command
			, @Valid YoutubeSearchForm youtubeSearchForm
			, BindingResult result
			) throws IllegalRequestException, IOException {
		
		validateAccess(id);

		if (result.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("youtubeSearchForm", youtubeSearchForm);
			modelAndView.setViewName("admin/article/newItem");
			return modelAndView;
		}
		if (command.equals("prev")) {
			youtubeSearchForm.prev();
		} else if (command.equals("next")) {
			youtubeSearchForm.next();
		}
		List<String> videoIds
			= this.searchYoutube(
					youtubeSearchForm.getSearchText(), youtubeSearchForm.getStartIndex(), youtubeSearchForm.getListSize());
		List<YoutubeItem> items = new ArrayList<YoutubeItem>(videoIds.size());
		for (String videoId : videoIds) {
			YoutubeItem item = new YoutubeItem();
			item.setArticleId(id);
			item.setVideoId(videoId);
			items.add(item);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("youtubeSearchForm", youtubeSearchForm);
		modelAndView.addObject("youtubeItems", items);
		modelAndView.setViewName("admin/article/newItem");
		return modelAndView;
	}

	public List<String> searchYoutube(String searchText, Integer startIndex, Integer listSize) throws IOException {

		// build the YouTube URL
		YouTubeSearchUrl url = new YouTubeSearchUrl();
		url.searchText = searchText;
		url.startIndex = startIndex;
		url.maxResults = listSize;

		// build the HTTP GET request
		HttpRequest request = gdataRequestFactory.buildGetRequest(url);
		request.addParser(new JsonCParser(new JacksonFactory()));

		logger.info("request url: " + request.getUrl().toString());

		// execute the request and the parse video feed
		VideoFeed feed = request.execute().parseAs(VideoFeed.class);
		
		// extract video ids
		List<String> videoIds = new ArrayList<String>();
		for (Video video : feed.items) videoIds.add(video.id);

		return videoIds;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/article/{id}/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(
			@PathVariable String id, @RequestParam("videoId") String videoId)
					throws IllegalRequestException {

		validateAccess(id);

		if (youtubeItemMapper.countByArticleIdAndVideoId(id, videoId) > 0) {
			logger.info("The video for articldId="+id+", videoId="+videoId+" already exists.");
		} else {
			YoutubeItem item = new YoutubeItem();
			Integer newItemId = youtubeItemMapper.newId();
			item.setId(newItemId.toString());
			item.setArticleId(id);
			item.setVideoId(videoId);
			youtubeItemMapper.create(item);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("updated", true);
		modelAndView.setViewName("forward:edit");
		return modelAndView;
	}
}
