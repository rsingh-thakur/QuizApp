package com.nrt.quiz.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.nrt.quiz.entity.Questions;
import com.nrt.quiz.response.ApiResponse;

@Component
public interface QuestionService {

	ResponseEntity<ApiResponse<Questions>> addQuestion(Questions question);

	ResponseEntity<ApiResponse<Questions>> updateQuestion(Long questionId, Questions question);

	ResponseEntity<ApiResponse<List<Questions>>> getAllQuestionzes();

	ResponseEntity<ApiResponse<Questions>> getQuestion(Long questionId);

	ResponseEntity<ApiResponse<?>> deleteQuestion(Long questionId);

}
