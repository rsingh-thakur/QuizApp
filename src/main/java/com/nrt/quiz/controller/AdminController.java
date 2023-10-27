package com.nrt.quiz.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class AdminController {

	@GetMapping("/aDash")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView getAdminDashboard(ModelAndView modelAndView) {
		log.info("adminDashboard controller invoked ..");
		modelAndView.setViewName("/html/Dashboards/adminDashboard");
		return modelAndView;
	}

	@GetMapping("/getCommonHeader")
	public String getHeader() {
		log.info("getCommonHeader controller invoked ..");
		return "/header.html";

	}

	@GetMapping("/getCommonSidebar")
	public String getSidebar() {
		log.info("getCommonSidebar controller invoked ..");
		return "/sideBar.html";
	}
}
