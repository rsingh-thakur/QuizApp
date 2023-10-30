package com.nrt.quiz.response;

import java.time.LocalDate;

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

	private LocalDate created_At;

	private String Address;

	private String phone;
	
	private String status;

	private String role;

}
