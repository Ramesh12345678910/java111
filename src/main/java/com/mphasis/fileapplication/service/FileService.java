package com.mphasis.fileapplication.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mphasis.fileapplication.model.dto.FileEntityRequestDTO;
import com.mphasis.fileapplication.model.entity.FileEntity;
@Service
public interface FileService {
	    
		FileEntity createFile(FileEntityRequestDTO req);
		void deleteFile(Long id);
		List<FileEntity> searchFiles(Long id, String filename, String status, Integer recordCount, LocalDate loaddate);
		void updatestatus(Long id,String Status);
		void archieveFile(Long id) ;
		FileEntity getFile(Long id);
}