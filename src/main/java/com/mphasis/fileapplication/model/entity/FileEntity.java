package com.mphasis.fileapplication.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="FileManager")
@Data

public class FileEntity {
	
	private String criteria;
	
	public FileEntity(String criteria) {
		this.criteria = criteria;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public FileEntity(Long id, String filename, LocalDate loadDate, String status, Integer recordCount,
			List<String> errors) {
		super();
		this.id = id;
		this.filename = filename;
		this.loadDate = loadDate;
		this.status = status;
		this.recordCount = recordCount;
		this.errors = errors;
	}
	
	public FileEntity() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column
	private String filename;
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
	public LocalDate getLoadDate() {
		return loadDate;
	}
	public void setLoadDate(LocalDate localDate) {
		this.loadDate = localDate;
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
	@Column
	private LocalDate loadDate;
	@Column
	private String status;
	@Column
	private Integer recordCount;
	@ElementCollection
	private List<String> errors;
}

