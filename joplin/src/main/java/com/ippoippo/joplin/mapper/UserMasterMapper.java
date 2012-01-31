package com.ippoippo.joplin.mapper;

import com.ippoippo.joplin.dto.User;

public interface UserMasterMapper {

	public int newId();

	public User getUserById(Integer id);

	public void createUser(User user);

	public void deleteUser(Integer id);
}
