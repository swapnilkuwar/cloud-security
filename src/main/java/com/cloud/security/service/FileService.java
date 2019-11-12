package com.cloud.security.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cloud.security.model.MyDataset;
import com.cloud.security.model.MyFile;

public interface FileService {
	List<MyDataset> getLoggedInUserDataset();
	String addFile(int dataset_id, MultipartFile file);
	boolean encryptLoggedInUserDatasets(int time, String enc_type);
	boolean dectyptLoggedInUserDatasets();
	boolean removeFile(int file_id);
	boolean addDataset(MyDataset dataset);
	boolean removeDataset(MyDataset dataset);
	boolean decryptAllFiles();
	List<MyFile> getLoggedInUserFiles();
	MyFile getFile(int file_id);
}
