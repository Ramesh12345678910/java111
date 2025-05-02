package com.mphasis.fileapplication.api;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
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