package com.mphasis.fileapplication.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

import com.mphasis.fileapplication.exceptions.BadRequestException;
import com.mphasis.fileapplication.exceptions.ResourceNotFoundException;
import com.mphasis.fileapplication.model.dto.FileEntityRequestDTO;
import com.mphasis.fileapplication.model.entity.FileEntity;
import com.mphasis.fileapplication.service.FileService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/files")
@Tag(name = "File Management API", description = "Endpoints for managing files")
@OpenAPIDefinition(info = @Info(title = "Trade File Application", version = "1.0", description = "File Management System"))
public class FileController {
	private FileService fileservice;
	@Autowired
	public FileController(FileService fileservice) {

		this.fileservice = fileservice;
	}

	@Operation(summary = "Create item", description = "returns an item by id")
	@ApiResponse(responseCode = "200", description = "success")
	@PostMapping("/create")
	public FileEntity createFile(@Valid @RequestBody FileEntityRequestDTO filerequest) {
		if (filerequest == null 
				|| filerequest.getFilename() == null
				|| filerequest.getFilename().trim().isEmpty()
				||!filerequest.getFilename().matches("[a-zA-z0-9.]+")
				|| filerequest.getRecordCount()==null
				|| hasdecimal(filerequest.getRecordCount())
				||isvalid(filerequest.getStatus().toUpperCase())
				||filerequest.getRecordCount()<1
				||filerequest.getStatus()==null
				||filerequest.getStatus().trim().isEmpty()) {
			System.out.println(filerequest.getRecordCount());
			throw new BadRequestException("Invalid file request!");
		}
		else {
		return fileservice.createFile(filerequest);
		}
	}
	public boolean hasdecimal(double input) {
		if(input%1!=0) {
			return true;
		}
		else
			return false;
	}
	public boolean isvalid(String status) {
		if(status.equals("CREATED")|| status.equals("PROCESSING") || status.equals("COMPLETED")) {
			return false;
		}
		else
			return true;
	}
	
	@Operation(summary = "delete item", description = "returns an item by id")
	@ApiResponse(responseCode = "200", description = "success")
	@DeleteMapping("/delete/{id}")
	public void deleteFile(@PathVariable Long id) {
		fileservice.deleteFile(id);
	}
	@Operation(summary ="Search the item",description="returns an item by Criteria")
   @ApiResponse(responseCode ="200",description="success")
	@GetMapping("/search")
	public ResponseEntity<List<FileEntity>> searchFiles(
	    @RequestParam(required = false) Long id,
	    @RequestParam(required = false) String filename,
	    @RequestParam(required = false) String status,
	    @RequestParam(required = false) Integer recordCount,
	    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate loaddate
	){
	    List<FileEntity> results = fileservice.searchFiles(id, filename, status, recordCount, loaddate);
	    return ResponseEntity.ok(results);
	}
	@Operation(summary = "Update item", description = "Updates A Record")
	@ApiResponse(responseCode = "200", description = "success")
	@PutMapping("/{id}/update")
	public void updatestatus(@PathVariable Long id, @RequestParam String Status) {
		if (Status == null || Status.isEmpty()) {
			throw new IllegalArgumentException("Status cannot be empty!");
		}
		fileservice.updatestatus(id, Status);
	}

	@Operation(summary = "Archive File", description = "Archieved A File")
	@ApiResponse(responseCode = "200", description = "success")
	@PutMapping("/archieve/{id}")
	public void archiveFile(@PathVariable Long id) {
		fileservice.archieveFile(id);
	}

	@Operation(summary = "Retrive File", description = "Fetch A File By Using An Id")
	@ApiResponse(responseCode = "200", description = "success")
	@GetMapping("/getfile/{id}")
	public FileEntity getFile(@PathVariable Long id) {
		FileEntity file = fileservice.getFile(id);
		if (file == null) {
			throw new ResourceNotFoundException("File with ID " + id + " not found!");
		}
		return file;
	}

}