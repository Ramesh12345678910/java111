package com.mphasis.fileapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mphasis.fileapplication.dao.UserRepositary;
import com.mphasis.fileapplication.model.entity.UserEntity;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepositary userrepositary;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	
	@Override
	public UserEntity registerUser(String username,String password) {
		UserEntity user=new UserEntity();
		user.setUsername(username);
		user.setPassword(passwordencoder.encode(password));
		user.setRole("User");
		return userrepositary.save(user);
		
	}
	@Override
	public UserEntity getUser(String username) {
		UserEntity user=userrepositary.findByUsername(username);
		return user;
		
	}
	@Override
	public void updateRole(String username,String newRole) {
		UserEntity user=userrepositary.findByUsername(username);
		if(user!=null) {
			user.setRole(newRole);
		}
			else {
				throw new RuntimeException("User not Found");
				
			}
		}
		
}
