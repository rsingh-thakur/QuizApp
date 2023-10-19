package com.nrt.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nrt.quiz.entity.Quiz;
import com.nrt.quiz.request.QuizRequest;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.QuizService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/quiz")
@Log4j2
public class QuizController {
	@Autowired
	private QuizService quizService;

	@PostMapping()
	public ResponseEntity<ApiResponse<Quiz>> addQuiz( @RequestBody QuizRequest quizRequest) {
//        return ResponseEntity.ok(this.quizService.addQuiz(quizEntity));
		log.info("data is "+quizRequest.toString());
		log.info("getCategoryId; is "+quizRequest.getCategoryId());
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

	@GetMapping("/page/{categoryId}")
	public ModelAndView getPageQuiz(ModelAndView modelAndView, @PathVariable("categoryId") String categoryId) {
		modelAndView.addObject("categoryId",categoryId);
		modelAndView.setViewName("html/QuizPages/quizByCategory");
		return modelAndView;
	}
	
	@GetMapping("/getAllQuizzes/{categoryId}")
	public ResponseEntity<ApiResponse<List<Quiz>>> getAllQuizzesUnderCategory(@PathVariable("categoryId") String categoryId) {
		return this.quizService.getAllQuizzesUnderCategory(categoryId);
	}

}
