package com.mphasis.fileapplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mphasis.fileapplication.model.entity.UserEntity;
@Repository
public interface UserRepositary extends JpaRepository<UserEntity,Long>{
	UserEntity findByUsername(String username);

}