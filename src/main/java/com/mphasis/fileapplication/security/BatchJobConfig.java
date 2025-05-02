package com.mphasis.fileapplication.security;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.mphasis.fileapplication.dao.FileRepositary;
import com.mphasis.fileapplication.model.entity.FileEntity;
 
@Configuration
public class BatchJobConfig {
 
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final FileRepositary fileRepository;
 
    public BatchJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, FileRepositary fileLoadRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.fileRepository = fileLoadRepository;
    }
 
    @Bean
    public Job fileLoadJob(Step fileLoadStep) {
        return new JobBuilder("fileLoadJob", jobRepository)
                .start(fileLoadStep)
                .build();
    }
 
    @Bean
    public Step fileLoadStep(ItemReader<FileEntity> reader, ItemWriter<FileEntity> writer,ItemProcessor<FileEntity,FileEntity> processor) {
        return new StepBuilder("fileLoadStep", jobRepository)
                .<FileEntity, FileEntity>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    
    @Bean
    public FlatFileItemReader<FileEntity> reader() {
        FlatFileItemReader<FileEntity> reader = new FlatFileItemReader<>();
        ClassPathResource resource = new ClassPathResource("fileload_data.csv");
 
        if (!resource.exists()) {
            throw new RuntimeException("File not found: " + resource.getFilename());
        }
 
        reader.setResource(resource);
        reader.setLinesToSkip(1); 
        reader.setLineMapper(new DefaultLineMapper<FileEntity>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("fileName", "status", "recordCount", "errors");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(FileEntity.class);
            }});
        }});
 
        return reader;
    }
    @Bean
   	public ItemProcessor<FileEntity, FileEntity> processor() {
   		return new FileLoadItemProcessor();
   	}
 
    @Bean
    public ItemWriter<FileEntity> writer() {
        return fileLoads -> fileRepository.saveAll(fileLoads);
    }
 
    
}
 