package com.nrt.quiz.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.nrt.quiz.entity.UserPlayedQuizHistory;
import com.nrt.quiz.request.UserPlayedQuizHistoryReq;
import com.nrt.quiz.response.ApiResponse;

import jakarta.servlet.http.HttpSession;

@Component
public interface UserPlayedQuizHistoryService {

	ResponseEntity<ApiResponse<UserPlayedQuizHistory>> addUserQuizHistory(UserPlayedQuizHistoryReq quizHistoryRequest, HttpSession session);

	ResponseEntity<ApiResponse<List<UserPlayedQuizHistory>>> getUserQuizHistory();

	ResponseEntity<ApiResponse<UserPlayedQuizHistory>> getUserQuizResult(String requestId);

	ResponseEntity<ApiResponse<Integer>> addUserRank(long quizId, HttpSession session);

}
