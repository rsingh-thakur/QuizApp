package com.nrt.quiz.response;

import java.sql.Date;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

	private String userToken;
	private Date date;

}
