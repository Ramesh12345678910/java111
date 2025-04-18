package com.mphasis.fileapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
@ComponentScan(basePackages= "com.mphasis.fileapplication")
@EnableJpaRepositories(basePackages="com.mphasis.fileapplication.dao")
@EntityScan(basePackages="com.mphasis.fileapplication.model.entity")
public class FileapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileapplicationApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode("1234");
		System.out.println("Hashed Password: " + hashedPassword);
		 
	}

}
