package com.ippoippo.joplin.jdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ippoippo.joplin.dto.Match;
import com.ippoippo.joplin.dto.YoutubeItem;

public interface YoutubeItemMapper {

	public Integer newId();

	public YoutubeItem getById(String id);

	public Integer countByArticleIdAndVideoId(@Param("articleId") String articleId, @Param("videoId") String videoId);

	public List<YoutubeItem> listByArticleId(String articleId);
	
	public void create(YoutubeItem youtubeItem);

	public void updateVideoId(@Param("id") String id, @Param("videoId") String videoId);

	public void delete(String id);

	public void updateRate(Match match);
}
