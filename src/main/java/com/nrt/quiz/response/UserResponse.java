package com.nrt.quiz.response;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UserResponse {

	private long userId;

	private String firstName;

	private String lastName;

	private String emailAddress;

	private String userType;

	private Date created_At;
	
	private String Address;
	
	private String phone;

	private String role;

}
