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
	public ModelAndView getAddQuizPage(ModelAndView modelAndView, @PathVariable("quizId") String quizId , HttpSession session) {
		
		String emailId = (String) session.getAttribute("email");
		if(emailId!=null) {
		modelAndView.addObject("quizId", quizId);
		modelAndView.setViewName("html/playQuiz/playQuiz");
		}
		else {
			modelAndView.setViewName("html/logins/login");
		}
		return modelAndView;

	}

	@PostMapping()
	public ResponseEntity<ApiResponse<UserPlayedQuizHistory>> addUserQuizHistory(
			@RequestBody UserPlayedQuizHistoryReq quizHistoryRequest, HttpSession session) {
		
		log.info("data is " + quizHistoryRequest);
		return this.userPlayedQuizHistoryService.addUserQuizHistory(quizHistoryRequest,session);

	}

	@GetMapping()
	public ResponseEntity<ApiResponse<List<UserPlayedQuizHistory>>> getUserQuizHistory() {
		return this.userPlayedQuizHistoryService.getUserQuizHistory();

	}

	@GetMapping("/{requestId}")
	public ResponseEntity<ApiResponse<UserPlayedQuizHistory>> getUserQuizResult( @PathVariable("requestId") String requestId) {
		
		return this.userPlayedQuizHistoryService.getUserQuizResult(requestId);

	}

	@GetMapping("/result/")
	public ModelAndView getResultPage(ModelAndView modelAndView, HttpSession session) {
		
		modelAndView.addObject("user", userService.getUserDetails((String) session.getAttribute("email")));
		modelAndView.setViewName("html/playQuiz/result");
		return modelAndView;

	}
	
	
	@GetMapping("/rank/{quizId}")
	public ResponseEntity<ApiResponse<Integer>> addUserRank(
			 @PathVariable("quizId") String quizId, HttpSession session) {
		log.info("addUserRank is " + quizId);
		long quizID = Long.parseLong(quizId);
		
		
		return this.userPlayedQuizHistoryService.addUserRank(quizID,session);

	}
	

}
