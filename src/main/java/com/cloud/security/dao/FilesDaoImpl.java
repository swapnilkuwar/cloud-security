package com.cloud.security.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.security.model.MyFile;


@Repository
@Transactional
public class FilesDaoImpl implements FilesDao{

	@Autowired
	SessionFactory session;
	
	@SuppressWarnings("unchecked")
	public List<MyFile> fileList() {
		return session.getCurrentSession().createQuery("from MyFile").list();
	}

	public boolean save(MyFile file) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(MyFile file) {
		// TODO Auto-generated method stub
		return false;
	}

	public MyFile getFile(int file_id) {
		// TODO Auto-generated method stub
		return null;
	}	
}
