package com.nrt.quiz.config;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CustomRequestFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	
		log.info("the josn requst to filter:"+request.getAttributeNames());
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Cleanup code (if needed)
	}

}