package com.cloud.security.service;

import com.cloud.security.model.User;

public interface UserService {

	User findById(int id);
	public User findByGID(String id);
	User findBySso(String sso);
	int getCurrentUserId();
	public boolean saveUser(User usr);
	User createUser(String id, String email, String firstName, String lastName);
}