package com.nrt.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nrt.quiz.entity.UserPlayedQuizHistory;
import com.nrt.quiz.request.UserPlayedQuizHistoryReq;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.UserPlayedQuizHistoryService;
import com.nrt.quiz.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/play")
@Log4j2
public class playQuizController {

	@Autowired
	UserPlayedQuizHistoryService userPlayedQuizHistoryService;
	@Autowired
	UserService userService;

	@GetMapping("/page/home")
	public ModelAndView getAddCategoryPage(ModelAndView modelAndView) {
		modelAndView.setViewName("html/playQuiz/playQuizHome");
		return modelAndView;

	}

	@GetMapping("/page/{quizId}")
	public ModelAndView getAddQuizPage(ModelAndView modelAndView, @PathVariable("quizId") String quizId) {
		modelAndView.addObject("quizId", quizId);
		modelAndView.setViewName("html/playQuiz/playQuiz");
		return modelAndView;

	}

	@PostMapping()
	public ResponseEntity<ApiResponse<UserPlayedQuizHistory>> addUserQuizHistory(
			@RequestBody UserPlayedQuizHistoryReq quizHistoryRequest) {
		log.info("data is " + quizHistoryRequest.toString());
		return this.userPlayedQuizHistoryService.addUserQuizHistory(quizHistoryRequest);

	}
	
	@GetMapping()
	public ResponseEntity<ApiResponse<List<UserPlayedQuizHistory>>> getUserQuizHistory() {
		return this.userPlayedQuizHistoryService.getUserQuizHistory();

	}
	
	
	@GetMapping("/result/")
	public ModelAndView getResultPage(ModelAndView modelAndView,HttpSession session) {
	  	modelAndView.addObject("user",userService.getUserDetails((String) session.getAttribute("email")));
		modelAndView.setViewName("html/playQuiz/result");
		return modelAndView;

	}

}
