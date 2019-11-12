package com.cloud.security.dao;

import java.util.List;

import com.cloud.security.model.MyFile;

public interface FilesDao {
	public boolean save(MyFile file);
	public boolean delete(MyFile file);
	public List<MyFile> fileList();
	public MyFile getFile(int file_id);
}
