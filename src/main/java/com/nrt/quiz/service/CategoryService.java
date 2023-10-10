package com.nrt.quiz.service;

import com.nrt.quiz.entity.Category;
import com.nrt.quiz.request.SearchPaginationRequest;
import com.nrt.quiz.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    public ResponseEntity<ApiResponse<Category>> addCategory(Category category);
    public ResponseEntity<ApiResponse<Category>> updateCategory(Long categoryId, Category category);
    public ResponseEntity<ApiResponse<Object>> getCategories(SearchPaginationRequest searchParams);
    public ResponseEntity<ApiResponse<Category>>  getCategory(Long categoryId);
    public ResponseEntity<ApiResponse<?>> deleteCategory(Long categoryId);
}
