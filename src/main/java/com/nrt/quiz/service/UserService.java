package com.nrt.quiz.service;

import org.springframework.web.multipart.MultipartFile;

import com.nrt.quiz.entity.User;
import com.nrt.quiz.request.UserRequest;

public interface UserService {
	
	public User createUser(UserRequest userRequst,MultipartFile file);

	public Boolean login(String userId, String password);

}
