package com.learningSpringBoot.redit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReditApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReditApplication.class, args);
	}

}
