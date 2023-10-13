package com.nrt.quiz.service;

import java.util.List;

import com.nrt.quiz.request.UserRequest;
import com.nrt.quiz.response.UserResponse;

public interface UserService {

	public UserResponse createUser(UserRequest userRequst);

	public Boolean login(String userId, String password);

	public List<UserResponse> getAllUesrsList();

	public UserResponse getUserDetails(String userId);

}
