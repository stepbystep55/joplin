package com.ippoippo.joplin.mongo.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.ippoippo.joplin.dto.Contribution;

public class ContributionOperations extends AbstractOperations {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ContributionOperations(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	public Long countByArticleIdAndUserId(String articleId, String userId) {
		Query query = Query.query(Criteria.where("articleId").is(articleId).and("userId").is(userId));
		return mongoOperations().count(query, Contribution.class.getSimpleName());
	}

	public Contribution getByArticleIdAndUserId(String articleId, String userId) {
		Query query = Query.query(Criteria.where("articleId").is(articleId).and("userId").is(userId));
		return mongoOperations().findOne(query, Contribution.class, Contribution.class.getSimpleName());
	}

	public List<Contribution> listByArticleId(String articleId) {
		Query query = Query.query(Criteria.where("articleId").is(articleId));
		return mongoOperations().find(query, Contribution.class, Contribution.class.getSimpleName());
	}

	public List<Contribution> listByArticleIdAndUserIds(String articleId, Set<String> userIds) {
		List<String> userIdList = new ArrayList<String>(userIds); // to avoid a bug of spring data mongo
		Query query = Query.query(Criteria.where("articleId").is(articleId).and("userId").in(userIdList));
		return mongoOperations().find(query, Contribution.class, Contribution.class.getSimpleName());
	}

	public void updateRank(String id, long rank) {
		Query query = Query.query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("rank", rank);
		mongoOperations().updateFirst(query, update, Contribution.class.getSimpleName());
	}

	public void create(Contribution contribution) {
		mongoOperations().insert(contribution, Contribution.class.getSimpleName());
	}

	public void deleteByUserId(String userId) {
		Query query = Query.query(Criteria.where("userId").is(userId));
		mongoOperations().remove(query, Contribution.class.getSimpleName());
	}
}
