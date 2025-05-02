package com.mphasis.fileapplication.security;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.mphasis.fileapplication.model.entity.FileEntity;
/*
 * @Component public class FileLoadItemProcessor implements
 * ItemProcessor<FileEntity,FileEntity> {
 * 
 * @Autowired private JdbcTemplate jdbcTemplate;
 * 
 * public FileEntity process(FileEntity item) throws Exception { String
 * sql="SELECT COUNT(*) FROM file_manager where id=?"; Integer
 * count=jdbcTemplate.queryForObject(sql, Integer.class,item.getId()); return
 * (count!=null && count>0)?null:item; }
 * 
 * }
 */
@Component
public class FileLoadItemProcessor implements ItemProcessor<FileEntity, FileEntity> {

        @Autowired
        private JdbcTemplate jdbcTemplate;
        
        @Override
        public FileEntity process(FileEntity item) throws Exception {
            String sql = "SELECT COUNT(*) FROM file_manager WHERE fileName = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, item.getFilename());

            // Log the processing logic for debugging
            System.out.println("Processing record with fileName: " + item.getFilename() + " | Count result: " + count);

            if (count != null && count > 0) {
                // Skip already processed records
                System.out.println("Skipping record with fileName: " + item.getFilename());
                return null;
            }

            // Process the new record
            System.out.println("Processing new record with fileName: " + item.getFilename());
            return item;
        }
    }