package com.mphasis.fileapplication.api;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.OPTIONS},
allowedHeaders="*",allowCredentials="true")
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
 
    @GetMapping("/run")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<String> startBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(fileLoadJob, jobParameters);
 
            return ResponseEntity.ok("Batch job has been started successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Batch job failed to start: " + e.getMessage());
        }
    }
}