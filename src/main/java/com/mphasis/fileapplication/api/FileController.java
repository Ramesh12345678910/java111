package com.mphasis.fileapplication.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mphasis.fileapplication.model.dto.FileEntityRequestDTO;
import com.mphasis.fileapplication.model.dto.SearchCriteriaDTO;
import com.mphasis.fileapplication.model.entity.FileEntity;
import com.mphasis.fileapplication.service.FileService;
@RestController
@RequestMapping("/files")
public class FileController {
		
		private final FileService fileservice;
		
		
		@Autowired
		public FileController(FileService fileservice) {
			
			this.fileservice = fileservice;
		}
		@PostMapping("/create")
		public FileEntity createFile(@RequestBody FileEntityRequestDTO filerequest) {
			return fileservice.createFile(filerequest);
		}
		@DeleteMapping("/delete/{id}")
		public void deleteFile(@PathVariable Long id) {
			fileservice.deleteFile(id);
		}
		@GetMapping("/search/{criteria}")
		public List<FileEntity>searching(@PathVariable SearchCriteriaDTO criteria){
		    return fileservice.searching(criteria);
		}
		@PutMapping("/{id}/update")
		public void updatestatus(@PathVariable Long id,@RequestParam String Status) {
			fileservice.updatestatus(id,Status);
		}
		@PutMapping("/archieve/{id}")
		public void archiveFile(@PathVariable Long id) {
			fileservice.archieveFile(id);
		}
		@GetMapping("/getfile/{id}")
		public FileEntity getFile(@PathVariable Long id) {
			return fileservice.getFile(id);
		}
		
}