package com.mphasis.fileapplication.api;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchJobController {

    private final JobLauncher jobLauncher;
    private final Job myJob;

    public BatchJobController(JobLauncher jobLauncher, Job myJob) {
        this.jobLauncher = jobLauncher;
        this.myJob = myJob;
    }

    @PostMapping("/batchjob")
    public ResponseEntity<String> triggerBatchJob(@RequestParam(required = false) String fileName) {
        try {
            // Build job parameters (e.g., pass fileName to the job if needed)
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis())) // Unique ID for each job run
                    .addString("fileName", fileName) // Optional parameter for the file
                    .toJobParameters();

            // Run the job
            jobLauncher.run(myJob, jobParameters);

            return ResponseEntity.ok("Batch job triggered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to trigger batch job: " + e.getMessage());
        }
    }
}