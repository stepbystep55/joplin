package com.ippoippo.joplin.jdbc.mapper;

import com.ippoippo.joplin.dto.User;

public interface UserMasterMapper {

	public Integer newId();

	public User getById(String id);

	public void create(User user);

	public void delete(String id);
}
