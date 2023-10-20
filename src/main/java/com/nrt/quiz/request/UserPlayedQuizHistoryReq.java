package com.nrt.quiz.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPlayedQuizHistoryReq {

	private String correctAnswers;

	private String attemptQuestions;

	private String attemptQuiz;
}
