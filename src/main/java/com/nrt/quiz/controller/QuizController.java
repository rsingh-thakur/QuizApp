package com.nrt.quiz.controller;

import com.nrt.quiz.entity.Quiz;
import com.nrt.quiz.request.QuizRequest;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.QuizService;

import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/quiz")
@Log4j2
public class QuizController {
	@Autowired
	private QuizService quizService;

	@PostMapping()
	public ResponseEntity<ApiResponse<Quiz>> addQuiz(@RequestBody QuizRequest quizRequest) {
//        return ResponseEntity.ok(this.quizService.addQuiz(quizEntity));
		log.info("data is "+quizRequest.toString());
		return this.quizService.addQuiz(quizRequest);
		
	}

	// update category
	@PutMapping("/{quizId}")
	public ResponseEntity<ApiResponse<Quiz>> updateQuiz(@PathVariable("quizId") Long quizId, @RequestBody Quiz quiz) {
		return this.quizService.updateQuiz(quizId, quiz);
	}

	// get all category
	@GetMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse<List<Quiz>>> getQuizzes() {
		return this.quizService.getAllQuizzes();
	}

	// get single category
	@GetMapping("/{quizId}")
	public ResponseEntity<ApiResponse<Quiz>> getQuiz(@PathVariable("quizId") Long quizId) {
		return this.quizService.get_Quiz(quizId);
	}

	@DeleteMapping("/{quizId}")
	public ResponseEntity<ApiResponse<?>> deleteQuiz(@PathVariable("quizId") Long quizId) {
		return this.quizService.deleteQuiz(quizId);
	}

	@GetMapping("/page")
	public ModelAndView getPageQuiz(ModelAndView modelAndView) {
		modelAndView.setViewName("html/QuizPages/ListQuiz");
		return modelAndView;
	}

}
