package com.nrt.quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class AdminController {

	@GetMapping("/aDash")
	public ModelAndView getAdminDashboard(ModelAndView modelAndView) {
		log.info("adminDashboard controller invoked ..");
		modelAndView.setViewName("/html/Dashboards/adminDashboard");
		return modelAndView;

	}
}
