package com.nrt.quiz;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class QuizAppApplication {

	public static void main(String[] args) {

		Logger log = (Logger) LoggerFactory.getLogger(QuizAppApplication.class);
		SpringApplication.run(QuizAppApplication.class, args);
		log.info("Quiz app started successfully .......");

	}

}
