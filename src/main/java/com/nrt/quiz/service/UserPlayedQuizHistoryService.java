package com.nrt.quiz.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.nrt.quiz.entity.UserPlayedQuizHistory;
import com.nrt.quiz.request.UserPlayedQuizHistoryReq;
import com.nrt.quiz.response.ApiResponse;

@Component
public interface UserPlayedQuizHistoryService {

	ResponseEntity<ApiResponse<UserPlayedQuizHistory>> addUserQuizHistory(UserPlayedQuizHistoryReq quizHistoryRequest);

	ResponseEntity<ApiResponse<List<UserPlayedQuizHistory>>> getUserQuizHistory();

}
