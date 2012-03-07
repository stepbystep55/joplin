package com.ippoippo.joplin.mongo.operations;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.ippoippo.joplin.dto.Article;

public class ArticleOperations {

	private MongoOperations mongoOperations;

	public ArticleOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
	
	/**
	 * get raw operations
	 * @return raw operations
	 */
	public MongoOperations raw() {
		return this.mongoOperations;
	}

	public List<Article> getActive() {
		Query query = Query.query(Criteria.where("active").is(true));
		return mongoOperations.find(query, Article.class, Article.class.getSimpleName());
	}
	
	public List<Article> listLatest(Integer start, Integer length) {
		Query query = new Query();
		if (start > 1) query.skip(start - 1);
		query.limit(length);
		query.sort().on("createdAt", Order.DESCENDING);
		return mongoOperations.find(query, Article.class, Article.class.getSimpleName());
	}

	public void create(Article article) {
		mongoOperations.insert(article, Article.class.getSimpleName());
	}

	public void updateSubjectAndActive(Article article) {
		Query query = Query.query(Criteria.where("_id").is(article.getId()));
		Update update = Update.update("subject", article.getSubject()).update("active", article.getActive());
		mongoOperations.updateFirst(query, update, Article.class.getSimpleName());
	}

	public void delete(String id) {
		mongoOperations.remove(Query.query(Criteria.where("_id").is(id)), Article.class.getSimpleName());
	}
}
