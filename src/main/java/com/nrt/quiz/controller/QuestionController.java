package com.nrt.quiz.controller;

import com.nrt.quiz.entity.Questions;
import com.nrt.quiz.request.QuestionRequest;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.QuestionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;

	@PostMapping()
	public ResponseEntity<ApiResponse<Questions>> addQuestion(@RequestBody QuestionRequest questionRequest) {
		// return ResponseEntity.ok(this.QuestionService.addQuestion(QuestionEntity));
		
		return this.questionService.addQuestion(questionRequest);
	}

	// update category
	@PutMapping("/{questionId}")
	public ResponseEntity<ApiResponse<Questions>> updateQuestion(@PathVariable("questionId") Long questionId,
			@RequestBody QuestionRequest Question) {
		return this.questionService.updateQuestion(questionId, Question);
	}

	// get all category
	@GetMapping("/")
	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse<List<Questions>>> getQuestion() {
		return this.questionService.getAllQuestionzes();
	}

	// get single category
	@GetMapping("/{questionId}")
	public ResponseEntity<ApiResponse<Questions>> getQuestion(@PathVariable("questionId") Long questionId) {
		return this.questionService.getQuestion(questionId);
	}

	@DeleteMapping("/{questionId}")
	public ResponseEntity<ApiResponse<?>> deleteQuestion(@PathVariable("questionId") Long questionId) {
		return this.questionService.deleteQuestion(questionId);
	}

	@GetMapping("/page")
	public ModelAndView getPageQuestion(ModelAndView modelAndView) {
		modelAndView.setViewName("html/QuestionPages/ListQuestion");
		return modelAndView;
	}
	

	@GetMapping("/page/{quizId}")
	public ModelAndView getPageQuestion(ModelAndView modelAndView, @PathVariable("quizId") String quizId) {
		modelAndView.addObject("quizId",quizId);
		modelAndView.setViewName("html/QuestionPages/questionPerQuiz");
		return modelAndView;
	}
	
	
	@GetMapping("/getAllQuestions/{quizId}")
	public ResponseEntity<ApiResponse<List<Questions>>> getQuestion(@PathVariable("quizId") String quizId) {
		return this.questionService.getAllQuestionByQuiz(quizId);
	}

}
