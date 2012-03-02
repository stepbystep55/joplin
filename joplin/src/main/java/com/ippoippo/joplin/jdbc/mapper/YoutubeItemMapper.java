package com.ippoippo.joplin.jdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ippoippo.joplin.dto.YoutubeItem;

public interface YoutubeItemMapper {

	public Integer newId();

	public YoutubeItem getById(String id);

	public Integer countByArticleIdAndVideoId(@Param("articleId") String articleId, @Param("videoId") String videoId);

	public List<YoutubeItem> listByArticleId(String articleId);
	
	public void rate(String articleId, String chosenItemId, String discardItemId);

	public void create(YoutubeItem youtubeItem);

	public void update(YoutubeItem youtubeItem);

	public void delete(String id);
}
