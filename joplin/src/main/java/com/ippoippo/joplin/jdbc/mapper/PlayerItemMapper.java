package com.ippoippo.joplin.jdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ippoippo.joplin.dto.Article;

public interface PlayerItemMapper {

	public Integer newId();

	public Article getById(String id);
	
	public List<Article> listLatest(@Param("start") Integer start, @Param("length") Integer length);

	public void create(Article article);

	public void update(Article article);

	public void delete(String id);
}
