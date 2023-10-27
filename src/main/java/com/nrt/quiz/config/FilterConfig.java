package com.nrt.quiz.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	private final CustomRequestFilter requestFilter;

	public FilterConfig(CustomRequestFilter requestFilter) {
		super();
		this.requestFilter = requestFilter;
	}

	@Bean
	FilterRegistrationBean<CustomRequestFilter> loggingFilter() {
		FilterRegistrationBean<CustomRequestFilter> registrationBean = new FilterRegistrationBean<CustomRequestFilter>();
		registrationBean.setFilter(requestFilter);
		registrationBean.addUrlPatterns("/"); // Specify the URL pattern to filter
		return registrationBean;
	}
}