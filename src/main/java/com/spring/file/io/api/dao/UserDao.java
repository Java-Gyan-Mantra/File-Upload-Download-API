package com.spring.file.io.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.file.io.api.model.UserModel;

@Repository
public interface UserDao extends JpaRepository<UserModel, Integer> {

}
