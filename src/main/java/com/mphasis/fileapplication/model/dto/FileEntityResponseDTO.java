package com.mphasis.fileapplication.model.dto;

import java.time.LocalDateTime;
import java.util.List;
@SuppressWarnings("unused")
public class FileEntityResponseDTO {
    private Long id;
    private String filename;
    private LocalDateTime loadDate;
    private String status;
    private Integer recordCount;
    private List<String> errors;

}
