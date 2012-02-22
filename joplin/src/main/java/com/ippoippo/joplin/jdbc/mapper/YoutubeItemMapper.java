package com.ippoippo.joplin.jdbc.mapper;

import java.util.List;

import com.ippoippo.joplin.dto.YoutubeItem;

public interface YoutubeItemMapper {

	public Integer newId();

	public YoutubeItem getById(String id);
	
	public List<YoutubeItem> listByArticleId(String articleId);

	public void create(YoutubeItem youtubeItem);

	public void update(YoutubeItem youtubeItem);

	public void delete(String id);
}
