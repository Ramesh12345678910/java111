package com.mphasis.fileapplication.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mphasis.fileapplication.model.dto.SearchCriteriaDTO;
import com.mphasis.fileapplication.model.entity.FileEntity;


@Repository
	public interface FileRepositary extends JpaRepository<FileEntity,Long> {
		public List<FileEntity> findByCriteria(SearchCriteriaDTO searchCriteria);
	}