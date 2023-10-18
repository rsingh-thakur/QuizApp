package com.nrt.quiz.service;

import com.nrt.quiz.entity.Quiz;
import com.nrt.quiz.request.QuizRequest;
import com.nrt.quiz.request.SearchPaginationRequest;
import com.nrt.quiz.response.ApiResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface QuizService {

    public ResponseEntity<ApiResponse<Quiz>> addQuiz(QuizRequest quizRequest);
    public ResponseEntity<ApiResponse<Quiz>> updateQuiz(Long quizId, Quiz quiz);
    public ResponseEntity<ApiResponse<Object>> getQuizzes(SearchPaginationRequest searchParams);
    public ResponseEntity<ApiResponse<Quiz>> get_Quiz(Long quizId);
    public ResponseEntity<ApiResponse<?>> deleteQuiz(Long quizId);

    //    public Set<QuizEntity> getQuizzes();
    public Quiz getQuiz(Long quizID);
	public ResponseEntity<ApiResponse<List<Quiz>>> getAllQuizzes();
}
