package com.mphasis.fileapplication.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mphasis.fileapplication.dao.FileRepositary;
import com.mphasis.fileapplication.exceptions.ResourceNotFoundException;
import com.mphasis.fileapplication.model.dto.FileEntityRequestDTO;
import com.mphasis.fileapplication.model.entity.FileEntity;

@Service
public class FileServiceImpl implements FileService {
	
	private FileRepositary filerepositary;
	
	@Autowired
	public FileServiceImpl(FileRepositary filerepositary) {
		this.filerepositary=filerepositary;
	}
	@Override
	public FileEntity createFile(FileEntityRequestDTO request) {
		if(request.getRecordCount()%1!=0) {
			throw new IllegalArgumentException("Cannot insert a decimal");
		}
		FileEntity entity=new FileEntity();
		entity.setFilename(request.getFilename());
		entity.setRecordCount(request.getRecordCount());
		entity.setLoadDate(LocalDate.now());
		entity.setStatus(request.getStatus());
		return filerepositary.save(entity);
	}
	@Override
	public void deleteFile(Long id) {
		filerepositary.findById(id).orElseThrow(() -> new RuntimeException("File not found with id: " + id));
		filerepositary.deleteById(id);

	}
	
	@Override
	public void updatestatus(Long id,String Status) {
		Status=Status.toUpperCase();
		FileEntity entity=filerepositary.findById(id).orElseThrow(null);
		if(Status.equals("CREATED")||Status.equals("PROCESSING") || Status.equals("COMPLETED")) {
			entity.setStatus(Status);
			filerepositary.save(entity);
		}
		else {
			throw new IllegalArgumentException("Invalid Request!");
		}
	}
	@Override
	public void archieveFile(Long id) {
		FileEntity entity=	filerepositary.findById(id).orElseThrow();
		entity.setStatus("archieved");
		filerepositary.save(entity);
		
	}
	@Override
	public FileEntity getFile(Long id) {
	        return filerepositary.findById(id).orElse(null);
	}
	@Override
	public List<FileEntity> searchFiles(Long id, String filename, String status, Integer recordCount, LocalDate loaddate) {
	    List<FileEntity> results = filerepositary.getSearchDetails(id, filename, status, recordCount, loaddate);
	    
	    if (results.isEmpty()) {
	        throw new ResourceNotFoundException("No matching records found!");
	    }
	    
	    return results;
	}



	
}
