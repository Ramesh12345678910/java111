package com.mphasis.fileapplication.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FileEntityResponseDTO {
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public LocalDateTime getLoadDate() {
		return loadDate;
	}
	public void setLoadDate(LocalDateTime loadDate) {
		this.loadDate = loadDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	private Long id;
    private String filename;
    private LocalDateTime loadDate;
    private String status;
    private Integer recordCount;
    private List<String> errors;

}
