package com.cloud.security.service;

public interface NotificationService {
	public boolean sendMail(String to, String subject, String msg);
}
