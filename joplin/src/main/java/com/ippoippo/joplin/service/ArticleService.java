package com.ippoippo.joplin.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
