package com.cloud.security.dao;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.security.model.Encryptor;
import com.cloud.security.model.MyDataset;
import com.cloud.security.model.MyFile;
import com.cloud.security.model.User;
import com.cloud.security.service.NotificationService;

@Repository("EncryptionDao")
public class EncryptionImpl implements Encryption {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	NotificationService notification;
	
	/*this method is only accesible for admin.
	 * this method encrypt all datasets of the current user. */
	public boolean encryptLoggedInUserDataset(int time, String enc_type) {
		int user_id = userDao.getCurrentUserId();
		Session s = sessionFactory.getCurrentSession().getSessionFactory().openSession();
		s.beginTransaction();
		User user = (User) s.get(User.class, user_id);
		List<MyDataset> set = user.getUserSet();
		Collections.sort(set);
		long availableTime = time;
		MyDataset current_Dataset;
		List<MyFile> current_files;
		boolean flag = false;
		for(int i=0; i< set.size(); i++) {
			
			if(availableTime <= 0) {
				break;
			}
			
			current_Dataset = set.get(i);
			System.out.println("Current Dataset ID: " + current_Dataset.getId());
			current_files = current_Dataset.getUserFiles();
			Collections.sort(current_files);
			for(int j=0; j<current_files.size(); j++) {
				
				Instant beforeEnc = Instant.now();
				
				if(current_files.get(j).isEncryptedStatus() == false) {
					
					MyFile currentFile = current_files.get(j);
					
					if(enc_type.equals(new String("AES"))) {
						if(availableTime < currentFile.getAes_enc_time()) {
							continue;
						}
					}else if(enc_type.equals("RSA")) {
						if(availableTime < currentFile.getRsa_enc_time()) {
							continue;
						}
					}else if(enc_type.equals("CPABE")) {
						if(availableTime < currentFile.getCpabe_enc_time()) {
							continue;
						}
					}
					
					Instant before = Instant.now();
					boolean status = Encryptor.encrypt(enc_type,currentFile);
					Instant after = Instant.now();
					
					if(status) {

						long time_diff = Duration.between(before, after).toMillis();
					
						if(enc_type.equals(new String("AES"))) {
							currentFile.setAes_enc_time(time_diff);
						}else if(enc_type.equals("RSA")) {
							currentFile.setRsa_enc_time(time_diff);
						}else if(enc_type.equals("CPABE")) {
							currentFile.setCpabe_enc_time(time_diff);
						}
						
						currentFile.setEnc_type(enc_type);
						currentFile.setEncryptedStatus(true);
						currentFile.setFilepath(currentFile.getFilepath() + "_enc");
						s.saveOrUpdate(currentFile);
					}
				}
				
				Instant afterEnc = Instant.now();
				long enc_time_diff = Duration.between(beforeEnc, afterEnc).toMillis();
				availableTime -= enc_time_diff;
				if(availableTime <= 0) {
					flag = true;
					break;
				}
			}
			if(flag) {
				break;
			}
		}
		s.getTransaction().commit();
		notification.sendMail(user.getEmail(), "Encryption Status", "Your Files Are Encrypted Successfully");
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean decryptAllFiles() {
		Session s = sessionFactory.getCurrentSession().getSessionFactory().openSession();
		List<MyFile> list = s.createQuery("from MyFile").list();
		s.beginTransaction();
		for(int i=0; i< list.size(); i++) {
			MyFile inputFile = list.get(i);
			if(inputFile.isEncryptedStatus()) {
				
				Instant before = Instant.now();
				boolean status = Encryptor.decrypt(inputFile);
				Instant after = Instant.now();
				
				if(status) {
					
					String enc_type = inputFile.getEnc_type();
					long time_diff = Duration.between(before, after).toMillis();
					
					if(enc_type.equals(new String("AES"))) {
						inputFile.setAes_dec_time(time_diff);
					}else if(enc_type.equals("RSA")) {
						inputFile.setRsa_dec_time(time_diff);
					}else if(enc_type.equals("CPABE")) {
						inputFile.setCpabe_dec_time(time_diff);
					}
					
					inputFile.setEncryptedStatus(false);
					inputFile.setFilepath(inputFile.getFilepath().substring(0, inputFile.getFilepath().length() - 4));
					s.save(inputFile);
				}
			}
		}
		s.getTransaction().commit();
		
		return true;
	}
}
