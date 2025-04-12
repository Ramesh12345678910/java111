package com.mphasis.fileapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mphasis.fileapplication.model.dto.FileEntityRequestDTO;
import com.mphasis.fileapplication.model.dto.SearchCriteriaDTO;
import com.mphasis.fileapplication.model.entity.FileEntity;
@Service
public interface FileService {
	    
		FileEntity createFile(FileEntityRequestDTO req);
		void deleteFile(Long id);
		List<FileEntity> searching(SearchCriteriaDTO criteria);
		void updatestatus(Long id,String Status);
		void archieveFile(Long id) ;
		FileEntity getFile(Long id);


}
