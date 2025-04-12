package com.mphasis.fileapplication.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class SearchCriteriaDTO {
	public String filename;
	public String Status;
	public Integer recordCount;
	private LocalDateTime startDate;
	private LocalDateTime EndDate;
}
