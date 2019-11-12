package com.cloud.security.dao;

public interface Encryption {
	boolean encryptLoggedInUserDataset(int time, String enc_type);
	boolean decryptAllFiles();
}
