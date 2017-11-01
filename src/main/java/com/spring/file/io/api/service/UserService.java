package com.spring.file.io.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.file.io.api.dao.UserDao;
import com.spring.file.io.api.model.UserModel;

@Service
public class UserService {
	@Autowired
	private UserDao dao;

	public void saveUser(String username, String filePath) {
		String fileId = UUID.randomUUID().toString();
		dao.save(new UserModel(username, filePath, fileId.split("-")[0]));
	}

	public List<UserModel> getAll() {
		return dao.findAll();
	}

	public String getSaveFilePathByUser(int id) {
		return dao.findOne(id).getFileStoredPath();
	}
}
