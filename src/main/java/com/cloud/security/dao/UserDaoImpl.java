package com.cloud.security.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.cloud.security.model.MyDataset;
import com.cloud.security.model.User;
import com.cloud.security.model.UserProfile;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	public User findById(int id) {
		return getByKey(id);
	}

	public User findBySSO(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		return (User) crit.uniqueResult();
	}
	
	public User findByGID(String id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("gId", id));
		return (User) crit.uniqueResult();
	}
	
	public boolean saveUser(User user) {
		SessionFactory session = getSessionFactory();
		Session ssn = session.openSession();
		ssn.beginTransaction();
		ssn.save(user);
		ssn.getTransaction().commit();
		ssn.close();
		session.close();
		return true;
	}

	public int getCurrentUserId() {
		Object principal1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User usr = this.findBySSO(((UserDetails) principal1).getUsername());
		return usr.getId();
	}

	public User createUser(String id, String email, String firstName, String lastName) {
		SessionFactory session = getSessionFactory();
		Session ssn = session.openSession();
		ssn.beginTransaction();
		
		User new_usr = new User();
		new_usr.setFirstName(firstName);
		new_usr.setLastName(lastName);
		new_usr.setEmail(email);
		new_usr.setgId(id);
		new_usr.setSsoId(email);
		new_usr.setPassword(email + id);
		new_usr.setState("Active");
		Set<UserProfile> set = new HashSet<UserProfile>();
		set.add(new UserProfile(1, "USER"));
		set.add(new UserProfile(2, "ADMIN"));
		new_usr.setUserProfiles(set);
		
		ssn.save(new_usr);
		ssn.getTransaction().commit();
		ssn.close();
		session.close();		
		return new_usr;
	}
}
