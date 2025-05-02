package com.mphasis.fileapplication.model.entity;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
//import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "FileManager")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String filename;

    @Column
    @CreationTimestamp
    private LocalDate loadDate;

    @Column
    private String status;

    @Column
    private Integer recordCount;
    @Column
    private List<String> errors;

    @Column
    private String criteria;

    // Default Constructor
    public FileEntity() {
        super();
    }

    // Constructor with criteria
    public FileEntity(String criteria) {
        this.criteria = criteria;
    }

    // Constructor with all fields
    public FileEntity(Long id, String filename, LocalDate loadDate, String status, Integer recordCount, List<String> errors) {
        this.id = id;
        this.filename = filename;
        this.loadDate = loadDate;
        this.status = status;
        this.recordCount = recordCount;
        this.errors = errors;
    }

    // Getters and Setters
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

    public void setLoadDate(LocalDate loadDate) {
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

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
