package com.nrt.quiz.controller;

import com.nrt.quiz.entity.Category;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	// add category
	@PostMapping()
	public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody Category category) {
		return this.categoryService.addCategory(category);
	}

	// update category
	@PutMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable("categoryId") Long categoryId,
			@RequestBody Category category) {
		return this.categoryService.updateCategory(categoryId, category);
	}

	// get all category
	@GetMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse<List<Category>>> getCategories() {
		// int pno = Integer.parseInt(pageNo);
		return this.categoryService.getCategories();
	}

	// get single category
	@GetMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<Category>> getCategory(@PathVariable("categoryId") Long categoryId) {
		return this.categoryService.getCategory(categoryId);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<?>> deleteCategory(@PathVariable("categoryId") Long categoryId) {
		return this.categoryService.deleteCategory(categoryId);
	}

	@GetMapping("/page")
	public ModelAndView getAddCategoryPage(ModelAndView modelAndView) {
		modelAndView.setViewName("html/CategoryPages/AddCategory");
		return modelAndView;

	}

	@GetMapping("/page/getAllCategories")
	public ModelAndView getAllCategoriesPage(ModelAndView modelAndView) {
		modelAndView.setViewName("html/CategoryPages/ListCategory");
		return modelAndView;

	}
}
