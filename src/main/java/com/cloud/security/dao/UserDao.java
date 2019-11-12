package com.cloud.security.dao;

import com.cloud.security.model.User;

public interface UserDao {

	User findById(int id);
	User findBySSO(String sso);
	boolean saveUser(User usr);
	int getCurrentUserId();
	public User findByGID(String id);
	User createUser(String id, String email, String firstName, String lastName);
}

