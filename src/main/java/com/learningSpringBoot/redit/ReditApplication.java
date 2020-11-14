package com.learningSpringBoot.redit;

import com.learningSpringBoot.redit.configs.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfig.class)
public class ReditApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReditApplication.class, args);
	}

}
