package com.nrt.quiz.request;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Component
@JsonDeserialize
public class LoginRequest {
	
	private String email;

	private String password;

	@JsonCreator
	public LoginRequest(@JsonProperty("email") String email, @JsonProperty("password") String password) {
		this.email = email;
		this.password = password;
	}
}