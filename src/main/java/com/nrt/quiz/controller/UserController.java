package com.nrt.quiz.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nrt.quiz.entity.User;
import com.nrt.quiz.request.LoginRequest;
import com.nrt.quiz.request.UserRequest;
import com.nrt.quiz.response.LoginResponse;
import com.nrt.quiz.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	// for index page
	@RequestMapping("/")
	public String homePage() {
		return "index";
	}

	// for user login page
	@GetMapping("/page/login")
	public ModelAndView getLoginPage(ModelAndView modelAndView) {
		modelAndView.setViewName("html/logins/login");
		return modelAndView;

	}

	// for user registration page
	@GetMapping("/page/registration")
	public ModelAndView getRegistrationPage(ModelAndView modelAndView) {
		log.info("registration controller invoked ..");
		modelAndView.setViewName("html/logins/registration");
		return modelAndView;

	}

	@PostMapping("/register")
	public ModelAndView createNewUser(@ModelAttribute UserRequest userRequest, ModelAndView modelAndView,
			@RequestParam("image") MultipartFile file) {
		User user = userService.createUser(userRequest, file);
		if (user != null) {
			modelAndView.addObject("successMessage", "Your Registration Completed successfully ..!");
			modelAndView.setViewName("html/logins/login");
			return modelAndView;
		} else {
			modelAndView.addObject("successMessage", "failed to create user ..");
			modelAndView.setViewName("html/logins/responsePage");
			return modelAndView;
		}

	}

	@SuppressWarnings("deprecation")
	@GetMapping("/login")
	public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest loginRequest) {
		log.info("login controller invoked ..");

		Boolean isLoggedIn = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
		if (isLoggedIn) {
			return new ResponseEntity<LoginResponse>(
					new LoginResponse("jwtToken will be passed", new Date(new java.util.Date().getDate())),
					HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<LoginResponse>(HttpStatusCode.valueOf(403));

	}

}
