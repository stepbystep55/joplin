package com.ippoippo.joplin.jdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ippoippo.joplin.dto.MatchItem;

public interface MatchHistoryMapper {

	public Integer newId();

	public MatchItem getLatestChosenByUserIdAndArticleId(@Param("userId") String userId, @Param("articleId") String articleId);
	
	public List<String> listRestObjectId(@Param("userId") String userId, @Param("articleId") String articleId);

	public void createChosen(MatchItem matchItem);

	public void createDiscard(MatchItem matchItem);
}