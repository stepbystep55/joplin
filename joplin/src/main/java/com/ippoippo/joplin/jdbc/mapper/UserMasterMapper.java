package com.ippoippo.joplin.jdbc.mapper;

import com.ippoippo.joplin.dto.User;

public interface UserMasterMapper {

	public Integer newId();

	public User getUserById(String id);

	public void createUser(User user);

	public void deleteUser(String id);
}
