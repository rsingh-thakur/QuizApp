package com.nrt.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrt.quiz.entity.UserPlayedQuizHistory;

public interface UserPlayedQuizHistoryRepo  extends JpaRepository<UserPlayedQuizHistory, Long>{
	
	

}
