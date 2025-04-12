package com.mphasis.fileapplication.service;

import org.springframework.stereotype.Service;

import com.mphasis.fileapplication.model.entity.UserEntity;

@Service
public interface UserService {
	UserEntity registerUser(String username,String password);
	UserEntity getUser(String Username);
	void updateRole(String username,String newrole);
	
}
