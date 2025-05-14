package com.mphasis.fileapplication.api;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/batchjob")
public class BatchJobController {

    private final JobLauncher jobLauncher;
    private final Job fileLoadJob;

    @Autowired
    public BatchJobController(JobLauncher jobLauncher, Job fileLoadJob) {
        this.jobLauncher = jobLauncher;
        this.fileLoadJob = fileLoadJob;
    }

    @PostMapping("/run")
    public ResponseEntity<Map<String, String>> startBatchJob(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
        }

        try {
            // Save file temporarily
            String filePath = System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename();
            File dest = new File(filePath);
            file.transferTo(dest);

            // Pass the file path dynamically to Spring Batch job
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filePath", filePath) // Inject file path into batch job
                    .addLong("time", System.currentTimeMillis()) // Unique identifier
                    .toJobParameters();

            jobLauncher.run(fileLoadJob, jobParameters);

            return ResponseEntity.ok(Map.of("message", "Batch job started successfully", "filePath", filePath));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to upload file", "details", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Batch job failed", "details", e.getMessage()));
        }
    }
}
