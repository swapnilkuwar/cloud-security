package com.cloud.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.security.dao.Encryption;
import com.cloud.security.model.MyDataset;
import com.cloud.security.model.MyFile;
import com.cloud.security.model.User;

@Service("dataService")
@Transactional
public class DataServiceImpl implements DataService {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	UserService userService;
	
	@Autowired
	Encryption EncryptionDao;
	
	public List<MyDataset> getDatasets() {
		int usr = userService.getCurrentUserId();
		Session ssn = sessionFactory.getCurrentSession().getSessionFactory().openSession();
		User usr2 = (User) ssn.get(User.class, usr);
		
		return usr2.getUserSet();
	}
	
	public boolean saveFileInDataset(MyFile file, int dataset_id) {
		int usr = userService.getCurrentUserId();
		Session ssn = sessionFactory.getCurrentSession().getSessionFactory().openSession();
		ssn.beginTransaction();
		User usr2 = (User) ssn.get(User.class, usr);
		System.out.println("user getted");
		List<MyDataset> datasets = usr2.getUserSet();
		System.out.println("datasets getted");
		List<MyFile> list;
		if(!datasets.isEmpty()) {
			ListIterator<MyDataset> iterator = datasets.listIterator();
			MyDataset dataset = null;
			while(iterator.hasNext()) {
				dataset = iterator.next();
				if(dataset_id == dataset.getId()) {
					System.out.println("breaked at "+ dataset_id);
					break;
				}else {
					dataset= null;
				}
			}
			System.out.println("dataset getted");
		
			list = dataset.getUserFiles();
			System.out.println("file list getted");
		
			int f = 0;
			if(!list.isEmpty()) {
				list.add(file);
				for(int i=0; i<list.size(); i++) {
					f+=list.get(i).getEncTime();
				}
			}else{
				System.out.println("in else");
				
				list = new ArrayList<MyFile>();
				list.add(file);
				f += file.getEncTime();
				dataset.getUserFiles().add(file);
				System.out.println("setted");
				
			}
			dataset.setEncryption_time(f);
			System.out.println("before commit");
		}
		ssn.getTransaction().commit();
		return true;
	}
	public boolean encryptLoggedInUserDataset(int time, String enc_type) {
		return EncryptionDao.encryptLoggedInUserDataset(time, enc_type);
	}

	public boolean decryptAllFiles() {
		return EncryptionDao.decryptAllFiles();
	}

	public MyFile getFile(int file_id) {
		Session ssn = sessionFactory.getCurrentSession().getSessionFactory().openSession();
		return (MyFile) ssn.get(MyFile.class, file_id);
		
	}
	
	public boolean calcEncTime() {
		int usr = userService.getCurrentUserId();
		Session ssn = sessionFactory.getCurrentSession().getSessionFactory().openSession();
		User usr2 = (User) ssn.get(User.class, usr);
		ssn.beginTransaction();
		List<MyDataset> dataset = usr2.getUserSet();
		for(int s=0; s<dataset.size(); s++) {
			List<MyFile> list = dataset.get(s).getUserFiles();
			int f = 0;
			for(int i=0; i<list.size(); i++) {
				f+=list.get(i).getEncTime();
			}
			dataset.get(s).setEncryption_time(f);
		}
		
		ssn.getTransaction().commit();
		ssn.close();
		return true;
	}

	public void createDataset(int weight) {
		int usr = userService.getCurrentUserId();
		Session ssn = sessionFactory.getCurrentSession().getSessionFactory().openSession();
		User usr2 = (User) ssn.get(User.class, usr);
		ssn.beginTransaction();
		List<MyDataset> datasets = usr2.getUserSet();
		MyDataset dataset = new MyDataset();
		dataset.setWeight(weight);
		datasets.add(dataset);
		usr2.setUserSet(datasets);
		
		ssn.save(usr2);
		ssn.getTransaction().commit();
		ssn.close();
	}
}
