package com.nrt.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrt.quiz.entity.Quiz;
import com.nrt.quiz.entity.UserPlayedQuizHistory;

public interface UserPlayedQuizHistoryRepo extends JpaRepository<UserPlayedQuizHistory, Long> {

	List<UserPlayedQuizHistory> findAllByAttemptQuiz(Quiz quiz);

}



