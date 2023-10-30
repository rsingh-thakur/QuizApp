package com.nrt.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nrt.quiz.entity.Quiz;
import com.nrt.quiz.request.QuizRequest;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.response.UserPlayedQuizHistoryResponse;
import com.nrt.quiz.service.QuizService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/quiz")
@Log4j2
public class QuizController {
	@Autowired
	private QuizService quizService;

	@PostMapping()
	@PreAuthorize("hasRole('ADMIN')  or hasRole('Quiz-ADD')")
	public ResponseEntity<ApiResponse<Quiz>> addQuiz(@RequestBody QuizRequest quizRequest) {
		log.info("data is " + quizRequest.toString());
		log.info("getCategoryId; is " + quizRequest.getCategoryId());
		return this.quizService.addQuiz(quizRequest);

	}

	// update category
	@PutMapping("/{quizId}")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('Quiz-UPDATE')")
	public ResponseEntity<ApiResponse<Quiz>> updateQuiz(@PathVariable("quizId") Long quizId, @RequestBody Quiz quiz) {
		return this.quizService.updateQuiz(quizId, quiz);
	}

	// get all category
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('Quiz-LIST')")
	public ResponseEntity<ApiResponse<List<Quiz>>> getQuizzes() {
		return this.quizService.getAllQuizzes();
	}

	// get single category
	@GetMapping("/{quizId}")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('Quiz-VIEW')")
	public ResponseEntity<ApiResponse<Quiz>> getQuiz(@PathVariable("quizId") Long quizId) {
		return this.quizService.get_Quiz(quizId);
	}

	@DeleteMapping("/{quizId}")
	public ResponseEntity<ApiResponse<?>> deleteQuiz(@PathVariable("quizId") Long quizId) {
		return this.quizService.deleteQuiz(quizId);
	}

	@GetMapping("/page")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('Quiz-VIEW')")
	public ModelAndView getPageQuiz(ModelAndView modelAndView) {
		modelAndView.setViewName("html/QuizPages/ListQuiz");
		return modelAndView;
	}

	@GetMapping("/page/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('Quiz-VIEW')")
	public ModelAndView getPageQuiz(ModelAndView modelAndView, @PathVariable("categoryId") String categoryId) {
		modelAndView.addObject("categoryId", categoryId);
		modelAndView.setViewName("html/QuizPages/quizByCategory");
		return modelAndView;
	}

	@GetMapping("/getAllQuizzes/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('Quiz-LIST')")
	public ResponseEntity<ApiResponse<List<Quiz>>> getAllQuizzesUnderCategory(
			@PathVariable("categoryId") String categoryId) {
		return this.quizService.getAllQuizzesUnderCategory(categoryId);
	}

	@GetMapping("/attemptQuizList")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('Quiz-VIEW')")
	public ResponseEntity<ApiResponse<List<UserPlayedQuizHistoryResponse>>> getAttemptQuizList() {
		return this.quizService.getAttemptQuizzesList();
	}

	@GetMapping("/page/history")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('Quiz-VIEW')")
	public ModelAndView getPageQuizHistory(ModelAndView modelAndView) {
		modelAndView.setViewName("html/playQuiz/playedQuizHistory");
		return modelAndView;
	}

	@GetMapping("updateStatus/{quizId}")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('Quiz-UPDATE')")
	public ResponseEntity<String> updateStatus(@PathVariable("quizId") String quizID) {
	   long quizId = Long.parseLong(quizID);
	   log.info("update method called");
		return this.quizService.changeStatus(quizId);
	}

}
