package com.ippoippo.joplin.mongo.operations;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.ippoippo.joplin.dto.Article;

public class ArticleOperations extends AbstractOperations {

	
	public ArticleOperations(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	public Article getById(String id) {
		Query query = Query.query(Criteria.where("id").is(id));
		return mongoOperations().findOne(query, Article.class, Article.class.getSimpleName());
	}

	public List<Article> getActive() {
		Query query = Query.query(Criteria.where("active").is(true));
		return mongoOperations().find(query, Article.class, Article.class.getSimpleName());
	}
	
	public List<Article> listLatest(Integer start, Integer length) {
		Query query = new Query();
		if (start > 1) query.skip(start - 1);
		query.limit(length);
		query.sort().on("createdAt", Order.DESCENDING);
		return mongoOperations().find(query, Article.class, Article.class.getSimpleName());
	}

	public void create(Article article) {
		mongoOperations().insert(article, Article.class.getSimpleName());
	}

	public void updateSubjectAndActive(Article article) {
		Query query = Query.query(Criteria.where("id").is(article.getId()));
		Update update = new Update();
		update.set("subject", article.getSubject()).set("active", article.getActive());
		mongoOperations().updateFirst(query, update, Article.class.getSimpleName());
	}

	public void delete(String id) {
		mongoOperations().remove(Query.query(Criteria.where("id").is(id)), Article.class.getSimpleName());
	}
}
