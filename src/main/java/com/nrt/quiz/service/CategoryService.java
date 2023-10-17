package com.nrt.quiz.service;

import com.nrt.quiz.entity.Category;
import com.nrt.quiz.response.ApiResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface CategoryService {
    public ResponseEntity<ApiResponse<Category>> addCategory(Category category);
    public ResponseEntity<ApiResponse<Category>> updateCategory(Long categoryId, Category category);
    public ResponseEntity<ApiResponse<Object>> getCategories(int pageNo);
    public ResponseEntity<ApiResponse<Category>>  getCategory(Long categoryId);
    public ResponseEntity<ApiResponse<?>> deleteCategory(Long categoryId);
	public ResponseEntity<ApiResponse<List<Category>>> getCategories();
}
