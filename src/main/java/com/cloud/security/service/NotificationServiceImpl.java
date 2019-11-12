package com.cloud.security.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;  

@Service("notificationService")
@Transactional
public class NotificationServiceImpl implements NotificationService{	 
	public boolean sendMail(String to, String subject, String msg) {
		return true;
	}
}	
