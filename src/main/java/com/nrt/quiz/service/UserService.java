package com.nrt.quiz.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nrt.quiz.request.LoginRequest;
import com.nrt.quiz.request.UserRequest;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.response.LoginResponse;
import com.nrt.quiz.response.UserResponse;


public interface UserService {

	public UserResponse createUser(UserRequest userRequst);

	public Boolean login(String userId, String password);

	public List<UserResponse> getAllUesrsList();

	public UserResponse getUserDetails();

	public UserResponse updateUserDetails(UserRequest userRequest);

	public Boolean deleteUserRecord(long userId);

	public ResponseEntity<ApiResponse<LoginResponse>> generateToken(LoginRequest loginRequest);

	public UserResponse getUserDetails(long userID);

}
