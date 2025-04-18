package com.mphasis.fileapplication.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.mphasis.fileapplication.model.entity.FileEntity;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private EntityManagerFactory entityManagerFactory;
	
	public SpringBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,EntityManagerFactory entityManagerFactory) {
		super();
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.entityManagerFactory=entityManagerFactory;
	}
	@Bean
	public Job myjob(Step fileprocessingstep) {
		return jobBuilderFactory.get("FirstJob")
				.start(fileprocessingstep)
				.build();
		
	}
	@Bean
	public Step fileprocessingstep(PlatformTransactionManager transactionManager) {
	    return ((SimpleStepBuilder<FileEntity, FileEntity>) stepBuilderFactory.get("fileprocessingstep")
	            .<FileEntity, FileEntity>chunk(10)
	            .reader(reader())                  
	            .writer(writer(entityManagerFactory)) 
	            .transactionManager(transactionManager))
	            .faultTolerant()
	            .skip(Exception.class)             
	            .skipLimit(5)
	            .retry(Exception.class)            
	            .retryLimit(3)
	            .listener(skipListener())          
	            .build();
	}

	@Bean
	public FlatFileItemReader<FileEntity> reader(){
		FlatFileItemReader<FileEntity> reader=new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("data.csv"));
		reader.setLineMapper(lineMapper());
		return reader;
	}
	
	@Bean
	public LineMapper<FileEntity> lineMapper(){
		DefaultLineMapper<FileEntity> lineMapper=new DefaultLineMapper<>();
		FixedLengthTokenizer tokenizer=new FixedLengthTokenizer();
		tokenizer.setColumns(new Range[] {new Range(1,10),new Range(11,20)});
		tokenizer.setColumns(new Range[] {
		        new Range(1, 5),  
		        new Range(6, 15), 
		        new Range(16, 25), 
		        new Range(26, 35),
		        new Range(36, 40), 
		        new Range(41, 50) 
		});
		BeanWrapperFieldSetMapper<FileEntity> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(FileEntity.class);
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}
	
	@Bean
	public JpaItemWriter<FileEntity> writer(EntityManagerFactory entityManagerFactory){
		JpaItemWriter<FileEntity> writer=new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
		
	}
	@Bean
	public SkipListener<FileEntity, FileEntity> skipListener() {
	    return new SkipListener<FileEntity, FileEntity>() {
	        @Override
	        public void onSkipInRead(Throwable t) {
	            System.err.println("Skipped during reading: " + t.getMessage());
	        }

	        @Override
	        public void onSkipInWrite(FileEntity item, Throwable t) {
	            System.err.println("Skipped during writing: " + item + ", error: " + t.getMessage());
	        }

	        @Override
	        public void onSkipInProcess(FileEntity item, Throwable t) {
	            System.err.println("Skipped during processing: " + item + ", error: " + t.getMessage());
	        }
	    };
	    
	}
	@Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	
}
