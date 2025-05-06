package com.mphasis.fileapplication.dao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mphasis.fileapplication.model.entity.FileEntity;
@Repository
public interface FileRepositary extends JpaRepository<FileEntity, Long> {
    @Query(value = "SELECT * FROM file_manager f WHERE " +
           "(:id IS NULL OR f.id = :id) AND " +
           "(:filename IS NULL OR f.filename LIKE CONCAT('%', :filename, '%')) AND " +
           "(:status IS NULL OR f.status = :status) AND " +
           "(:recordCount IS NULL OR f.record_count = :recordCount) AND " +
           "(:loaddate IS NULL OR f.load_date = :loaddate)",
           nativeQuery = true)
    List<FileEntity> getSearchDetails(
        @Param("id") Long id,
        @Param("filename") String filename,
        @Param("status") String status,
        @Param("recordCount") Integer recordCount,
        @Param("loaddate") LocalDate loaddate
    );
}


