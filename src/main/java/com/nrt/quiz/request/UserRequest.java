package com.nrt.quiz.request;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserRequest {
	
	private String firstName;
	
	private String lastName;

	private String emailAddress;
	
	private String password;
	
	private String userType;
}
