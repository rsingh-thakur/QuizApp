package com.nrt.quiz.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserPlayedQuizHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int correctAnswers;

	private int wrongAnswers;

	private int attemptQuestions;

	private int score;

	private long attemptQuizId;

	private long userId;
}
