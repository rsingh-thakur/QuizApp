package com.nrt.quiz.service;

import com.nrt.quiz.entity.User;
import com.nrt.quiz.request.UserRequest;

public interface UserService {
	
	public User createUser(UserRequest userRequst);

}
