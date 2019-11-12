package com.cloud.security.service;

import java.util.List;

import com.cloud.security.model.MyDataset;
import com.cloud.security.model.MyFile;

public interface DataService {
	public List<MyDataset> getDatasets();
	public boolean encryptLoggedInUserDataset(int time, String enc_type);
	public boolean saveFileInDataset(MyFile file, int dataset_id);
	boolean decryptAllFiles();
	MyFile getFile(int file_id);
	public boolean calcEncTime();
	public void createDataset(int weight);
}