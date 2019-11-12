package com.cloud.security.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.security.model.MyDataset;
import com.cloud.security.model.MyFile;

@Service("fileService")
@Transactional
public class FileServiceImpl implements FileService {

	@Autowired
	DataService dataService;
	
	public List<MyDataset> getLoggedInUserDataset() {
		return dataService.getDatasets();
	}

	public String addFile(int dataset_id, MultipartFile file) {
		
		if (!file.isEmpty()) {
			String name = file.getOriginalFilename();
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();
				
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				MyFile tempFile = new MyFile();
				tempFile.setFilepath(serverFile.getAbsolutePath());
				tempFile.setSize((serverFile.length()/1024));
				tempFile.setEncTime(((serverFile.length()/1024)*500)/(1024*10));
				dataService.saveFileInDataset(tempFile, dataset_id);
				stream.close();
				return "Your file successfuly uploaded. File Name: " + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload. Because the file was empty.";
		}
	}

	public boolean encryptLoggedInUserDatasets(int time, String enc_type) {
		// TODO Auto-generated method stub
		return dataService.encryptLoggedInUserDataset(time, enc_type);
	}

	public boolean dectyptLoggedInUserDatasets() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeFile(int file_id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addDataset(MyDataset dataset) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeDataset(MyDataset dataset) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean decryptAllFiles() {
		// TODO Auto-generated method stub
		return dataService.decryptAllFiles();
	}

	public List<MyFile> getLoggedInUserFiles() {
		List<MyDataset> set = dataService.getDatasets();
		List<MyFile> files = new ArrayList<MyFile>();
		for(int i=0; i<set.size(); i++) {
			files.addAll(set.get(i).getUserFiles());
		}
		return files;
	}

	public MyFile getFile(int file_id) {
		return dataService.getFile(file_id);
	}
}
