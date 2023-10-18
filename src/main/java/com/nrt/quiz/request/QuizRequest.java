package com.nrt.quiz.request;

import org.springframework.stereotype.Component;

import lombok.Data;;

@Component
@Data
public class QuizRequest {

	private String name;
	private String description;
	private String maxMarks;
	private String numberOfQuestions;
	private String active;
	private long categoryId;
}
