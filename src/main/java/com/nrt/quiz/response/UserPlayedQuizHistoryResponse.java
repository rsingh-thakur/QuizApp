package com.nrt.quiz.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserPlayedQuizHistoryResponse {
	
	private int correctAnswers;

	private int wrongAnswers;

	private int attemptQuestions;

	private int score;

	private String attemptQuizName;
}