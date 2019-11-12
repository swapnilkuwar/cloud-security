package com.cloud.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.security.dao.UserDao;
import com.cloud.security.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
	
	public User findById(int id) {
		return dao.findById(id);
	}
	
	public User findByGID(String id) {
		return dao.findByGID(id);
	}

	public User findBySso(String sso) {
		return dao.findBySSO(sso);
	}

	public boolean saveUser(User usr) {
		return dao.saveUser(usr);
	}

	public int getCurrentUserId() {
		return dao.getCurrentUserId();
	}

	public User createUser(String id, String email, String firstName, String lastName) {
		// TODO Auto-generated method stub
		return dao.createUser(id, email, firstName, lastName);
	}
}