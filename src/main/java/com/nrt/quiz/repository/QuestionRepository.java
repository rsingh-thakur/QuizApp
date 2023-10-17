package com.nrt.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrt.quiz.entity.Questions;

public interface QuestionRepository extends JpaRepository<Questions, Long>{

}
