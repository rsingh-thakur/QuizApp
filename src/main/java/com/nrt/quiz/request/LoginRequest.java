package com.nrt.quiz.request;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class LoginRequest {

	private String email;
	private String password;
}
