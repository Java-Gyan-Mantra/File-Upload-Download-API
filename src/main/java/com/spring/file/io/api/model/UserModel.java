package com.spring.file.io.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserModel {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String fileStoredPath;
	private String fileID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileStoredPath() {
		return fileStoredPath;
	}

	public void setFileStoredPath(String fileStoredPath) {
		this.fileStoredPath = fileStoredPath;
	}

	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public UserModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserModel(String name, String fileStoredPath, String fileID) {
		super();
		this.name = name;
		this.fileStoredPath = fileStoredPath;
		this.fileID = fileID;
	}

}
