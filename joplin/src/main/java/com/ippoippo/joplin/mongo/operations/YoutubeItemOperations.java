package com.ippoippo.joplin.mongo.operations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.ippoippo.joplin.dto.YoutubeItem;

public class YoutubeItemOperations extends AbstractOperations {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public YoutubeItemOperations(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	public List<YoutubeItem> listByIds(List<String> ids) {
		Query query = Query.query(Criteria.where("id").in(ids));
		return mongoOperations().find(query, YoutubeItem.class, YoutubeItem.class.getSimpleName());
	}

	public Long countByArticleId(String articleId) {
		Query query = Query.query(Criteria.where("articleId").is(articleId));
		return mongoOperations().count(query, YoutubeItem.class.getSimpleName());
	}
	
	public Long countByArticleIdAndVideoId(String articleId, String videoId) {
		Query query = Query.query(Criteria.where("articleId").is(articleId).and("videoId").is(videoId));
		return mongoOperations().count(query, YoutubeItem.class.getSimpleName());
	}

	public Long rankForVideoId(String videoId) {
		Query query = Query.query(Criteria.where("videoId").is(videoId));
		YoutubeItem item = mongoOperations().findOne(query, YoutubeItem.class, YoutubeItem.class.getSimpleName());
		query = Query.query(Criteria.where("rate").gt(item.getRate()));
		return (mongoOperations().count(query, YoutubeItem.class.getSimpleName()) + 1);
	}

	public List<YoutubeItem> listByArticleId(String articleId) {
		Query query = Query.query(Criteria.where("articleId").in(articleId));
		return mongoOperations().find(query, YoutubeItem.class, YoutubeItem.class.getSimpleName());
	}
	
	public List<YoutubeItem> listByArticleId(String articleId, int startIndex, int limit) {
		Query query = Query.query(Criteria.where("articleId").in(articleId)).skip(startIndex - 1).limit(limit);
		return mongoOperations().find(query, YoutubeItem.class, YoutubeItem.class.getSimpleName());
	}
	
	public List<YoutubeItem> listTopRate(String articleId, int limit) {
		Query query = Query.query(Criteria.where("articleId").is(articleId)).limit(limit);
		query.sort().on("rate", Order.DESCENDING);
		return mongoOperations().find(query, YoutubeItem.class, YoutubeItem.class.getSimpleName());
	}

	public void create(YoutubeItem youtubeItem) {
		mongoOperations().insert(youtubeItem, YoutubeItem.class.getSimpleName());
	}

	public void updateVideoId(String id, String videoId) {
		Query query = Query.query(Criteria.where("id").is(id));
		mongoOperations().updateFirst(query, Update.update("videoId", videoId), YoutubeItem.class.getSimpleName());
	}

	public void delete(String id) {
		Query query = Query.query(Criteria.where("id").is(id));
		mongoOperations().remove(query, YoutubeItem.class.getSimpleName());
	}

	public void updateRate(String id, Double rateVaried) {
		Query query = Query.query(Criteria.where("id").is(id));
		Update update = new Update();
		update.inc("rate", rateVaried);
		mongoOperations().updateFirst(query, update, YoutubeItem.class.getSimpleName());
	}
}
