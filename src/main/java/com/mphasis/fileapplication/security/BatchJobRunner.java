package com.mphasis.fileapplication.security;

import org.springframework.batch.core.Job;
 
import org.springframework.batch.core.launch.JobLauncher;
 
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.boot.CommandLineRunner;
 
import org.springframework.stereotype.Component;

@Component
 
public class BatchJobRunner implements CommandLineRunner {
 

	private final JobLauncher jobLauncher;
 
    private final Job fileLoadJob;
    @Autowired
 
    public BatchJobRunner(JobLauncher jobLauncher, Job fileLoadJob) {
 
        this.jobLauncher = jobLauncher;
 
        this.fileLoadJob = fileLoadJob;
 
    }
    @Override
 
    public void run(String... args) throws Exception {
 
        jobLauncher.run(fileLoadJob, new org.springframework.batch.core.JobParameters());
 
    }
 
}
 