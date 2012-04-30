package com.ippoippo.joplin.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ippoippo.joplin.dto.Article;
import com.ippoippo.joplin.exception.IllegalOperationException;
import com.ippoippo.joplin.mongo.operations.ArticleOperations;
import com.ippoippo.joplin.mongo.operations.YoutubeItemOperations;

@Service
public class ArticleService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	YoutubeItemOperations youtubeItemOperations;

	@Inject
	ArticleOperations articleOperations;

	@Cacheable("article")
	public Article getById(String id) {
		logger.info("getting an article for cache: id=" + id);
		return articleOperations.getById(id);
	}

	public Article getByIdForUpdate(String id) {
		return articleOperations.getById(id);
	}

	@Cacheable("activeArticle")
	public Article getActive() {
		Article article = null;
		List<Article> articles = articleOperations.getActive();
		if (articles != null && articles.size() > 0) {
			article = articles.get(0);
		} else {
			article = new Article();
			article.setId(Article.ID_FOR_NO_ARTICLE);
		}
		logger.info("getting an active article: id=" + article.getId());
		return article;
	}

	public List<Article> listLatest(Integer start, Integer length) {
		return articleOperations.listLatest(0, 10);
	}

	public String create(Article article) {
		articleOperations.create(article);
		return article.getId();
	}

	public void delete(String id) {
		articleOperations.delete(id);
	}

	public void updateSubjectAndActive(Article article) throws IllegalOperationException {
		if (article.getActive()) {
			long videoCount = youtubeItemOperations.countByArticleId(article.getId());
			if (videoCount == 0) throw new IllegalOperationException("You can't activate an article with no video.");

			// as other articles must be inactive
			articleOperations.inactivateAllArticles();
		}
		articleOperations.updateSubjectAndActive(article);
	}
}
