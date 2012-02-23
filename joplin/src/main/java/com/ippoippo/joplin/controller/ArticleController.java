package com.ippoippo.joplin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ippoippo.joplin.youtube.Video;
import com.ippoippo.joplin.youtube.VideoFeed;
import com.ippoippo.joplin.youtube.YouTubeSearchUrl;

/**
 * Handles requests for articles.
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	ArticleMapper articleMapper;

	@Inject
	YoutubeItemMapper youtubeItemMapper;

	@Inject
	private HttpRequestFactory gdataRequestFactory;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView top() {
		return list();
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView list() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("articles", articleMapper.listLatest(0, 10));
		modelAndView.setViewName("article/list");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView create() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("article", new Article());
		modelAndView.setViewName("article/new");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@Valid Article article, BindingResult result) {

		if (result.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("article", article);
			modelAndView.setViewName("article/new");
			return modelAndView;
		}

		Integer newId = articleMapper.newId();
		article.setId(newId.toString());
		articleMapper.create(article);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("created", true);
		modelAndView.setViewName("forward:edit/"+newId);
		return modelAndView;
	}

	private void validateAccess(String articleId) throws IllegalRequestException {
		
		Article article = articleMapper.getById(articleId);
		if (article == null) throw new IllegalRequestException();
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView edit(@PathVariable String id) throws IllegalRequestException {

		validateAccess(id);

		Article article = articleMapper.getById(id);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("article", article);
		modelAndView.addObject("items", youtubeItemMapper.listByArticleId(article.getId()));
		modelAndView.setViewName("article/edit");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@Valid Article article, BindingResult result) throws IllegalRequestException {

		validateAccess(article.getId());

		if (result.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("article", article);
			modelAndView.addObject("items", youtubeItemMapper.listByArticleId(article.getId()));
			modelAndView.setViewName("article/edit");
			return modelAndView;
		}

		articleMapper.update(article);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("updated", true);
		modelAndView.setViewName("forward:edit/"+article.getId());
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView delete(@PathVariable String id) throws IllegalRequestException {
		
		validateAccess(id);

		articleMapper.delete(id);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("deleted", true);
		modelAndView.setViewName("forward:list");
		return modelAndView;
	}

	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/newItem", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView createItem(@RequestParam("articleId") String articleId) throws IllegalRequestException, IOException {

		validateAccess(articleId);

		YoutubeSearchForm form = new YoutubeSearchForm();
		form.setArticleId(articleId);
		

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("youtubeSearchForm", form);
		modelAndView.setViewName("article/newItem");
		return modelAndView;
	}
	
	@Transactional(rollbackForClassName="java.lang.Exception")
	@RequestMapping(value = "/searchItem", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView searchItem(
			@Valid YoutubeSearchForm youtubeSearchForm, BindingResult result) throws IllegalRequestException, IOException {
		
		validateAccess(youtubeSearchForm.getArticleId());

		if (result.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("youtubeSearchForm", youtubeSearchForm);
			modelAndView.setViewName("article/newItem");
			return modelAndView;
		}
		List<String> videoIds = this.searchYoutube(youtubeSearchForm.getSearchText(), youtubeSearchForm.getStartIndex(), youtubeSearchForm.getListSize());
		List<YoutubeItem> items = new ArrayList<YoutubeItem>(videoIds.size());
		for (String videoId : videoIds) {
			YoutubeItem item = new YoutubeItem();
			item.setArticleId(youtubeSearchForm.getArticleId());
			item.setVideoId(videoId);
			items.add(item);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("youtubeSearchForm", youtubeSearchForm);
		modelAndView.addObject("youtubeItems", items);
		modelAndView.setViewName("article/newItem");
		return modelAndView;
	}

	private static final Integer MAX_RESULTS = 10;

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
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(
			@RequestParam("articleId") String articleId, @RequestParam("videoId") String videoId) throws IllegalRequestException {

		validateAccess(articleId);

		YoutubeItem item = new YoutubeItem();
		Integer newId = youtubeItemMapper.newId();
		item.setId(newId.toString());
		youtubeItemMapper.create(item);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("updated", true);
		modelAndView.setViewName("forward:edit/"+item.getArticleId());
		return modelAndView;
	}
}
