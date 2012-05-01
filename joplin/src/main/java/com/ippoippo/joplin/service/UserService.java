package com.ippoippo.joplin.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ippoippo.joplin.dto.User;
import com.ippoippo.joplin.jdbc.mapper.UserMasterMapper;

@Service
public class UserService {

	@Inject
	UserMasterMapper userMasterMapper;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public User getById(String id) {
		return userMasterMapper.getById(id);
	}
}
