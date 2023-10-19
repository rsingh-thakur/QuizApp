package com.nrt.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrt.quiz.entity.Questions;
import com.nrt.quiz.entity.Quiz;

public interface QuestionRepository extends JpaRepository<Questions, Long>{

	List<Questions> findAllByQuiz(Quiz quiz);

}
