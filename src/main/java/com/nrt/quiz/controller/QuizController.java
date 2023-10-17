package com.nrt.quiz.controller;

import com.nrt.quiz.entity.Quiz;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.QuizService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/quiz")
public class QuizController {
	@Autowired
	private QuizService quizService;

	@PostMapping()
	public ResponseEntity<ApiResponse<Quiz>> addQuiz(@RequestBody Quiz quiz) {
//        return ResponseEntity.ok(this.quizService.addQuiz(quizEntity));
		return this.quizService.addQuiz(quiz);
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
