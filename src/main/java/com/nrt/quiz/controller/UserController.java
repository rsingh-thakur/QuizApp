package com.nrt.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nrt.quiz.entity.User;
import com.nrt.quiz.request.UserRequest;
import com.nrt.quiz.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
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
		log.info("home controller invoked ..");
		modelAndView.setViewName("html/logins/login");
		log.info(" 222home controller invoked ..");
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

	@PostMapping("/login")
	public ModelAndView userLogin(ModelAndView modelAndView, @RequestParam("userId") String userId,
			@RequestParam("password") String password) {
		log.info("login controller invoked ..");

		Boolean isLoggedIn = userService.login(userId, password);
		if (isLoggedIn) {

			modelAndView.setViewName("/html/Dashboards/adminDashboard");
			return modelAndView;
		} else
			modelAndView.addObject("successMessage", "login faild  ..");
		modelAndView.setViewName("html/logins/responsePage");
		return modelAndView;

	}

}
