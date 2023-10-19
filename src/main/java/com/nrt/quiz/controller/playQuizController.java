package com.nrt.quiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/play")
public class playQuizController {

	@GetMapping("/page/home")
	public ModelAndView getAddCategoryPage(ModelAndView modelAndView) {
		modelAndView.setViewName("html/playQuiz/playQuizHome");
		return modelAndView;

	}
}
