package com.mphasis.fileapplication.model.dto;
import jakarta.validation.constraints.Digits;
import lombok.Data;
@Data
public class FileEntityRequestDTO {
		private String filename;
		private String status;
		@Digits(integer=10,fraction=0)
		private Integer recordCount;
		
		public FileEntityRequestDTO(String filename, String status, Integer recordCount) {
			super();
			this.filename = filename;
			this.status = status;
			this.recordCount = recordCount;
		}
		public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
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
}
