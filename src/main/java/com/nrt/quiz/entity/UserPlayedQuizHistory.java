package com.nrt.quiz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userQuizHistory_details")
public class UserPlayedQuizHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int correctAnswers;
	
	private int wrongAnswers;
	
	private int  attemptQuestions;
	 
	private int score;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Quiz  attemptQuiz;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;
}
