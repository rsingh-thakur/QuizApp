package com.nrt.quiz.serviceImpl;

import com.nrt.quiz.entity.Category;
import com.nrt.quiz.repository.CategoryRepository;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public ResponseEntity<ApiResponse<Category>> addCategory(Category category) {
		try {
			Category payload = this.categoryRepository.save(category);
			return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while saving data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public ResponseEntity<ApiResponse<Category>> updateCategory(Long categoryId, Category category) {
		try {
			Optional<Category> existingCategory = this.categoryRepository.findById(categoryId);

			if (existingCategory.isPresent()) {
				// Update the existing category with the new data
				Category updatedCategory = existingCategory.get();
				updatedCategory.setTitle(category.getTitle());
				updatedCategory.setDescription(category.getDescription());
				// Save the updated category
				Category payload = this.categoryRepository.save(updatedCategory);

				return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("error", "Category not found", null, 404));
			}
		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while updating data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public ResponseEntity<ApiResponse<Object>> getCategories(int pageNo) {

		try {

			Integer page = (pageNo != 0) ? pageNo : 1;

			Page<Category> categoryPage;
			categoryPage = categoryRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id"))));
			List<Category> categoryPage1 = categoryRepository.findAll();
			log.info("the all recoreds are :"+categoryPage.getNumberOfElements());
			log.info("the all recoreds are :"+categoryPage1.indexOf(0));
			List<Category> categories = categoryPage.getContent();
			
			log.info("the list recoreds are :"+categories.indexOf(1));

			
			if(categories!=null)
				log.info("not null is found"+categories.size());
			
			
			for(Category cat : categories) {
				log.info(cat+ " record is here");
			}
			
			
			
			Map<String, Object> map = Map.of("data", categories, "totalElements", categoryPage.getTotalElements(),
					"currentPage", page, "perPageRecord", 10, "totalPages", categoryPage.getTotalPages());
			return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", map, 200));

		} catch (Exception e) {
			e.printStackTrace();
			log.error("An error occurred while saving data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}

	}

	@Override
	public ResponseEntity<ApiResponse<Category>> getCategory(Long categoryId) {
		try {
			Optional<Category> categoryOptional = this.categoryRepository.findById(categoryId);

			if (categoryOptional.isPresent()) {
				Category categoryEntity = categoryOptional.get();
				return ResponseEntity
						.ok(new ApiResponse<>("success", "Data retrieved successfully", categoryEntity, 200));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("error", "Category not found", null, 404));
			}
		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while retrieving data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public ResponseEntity<ApiResponse<?>> deleteCategory(Long categoryId) {
		try {
			Optional<Category> category = this.categoryRepository.findById(categoryId);
			if (category.isPresent()) {

				this.categoryRepository.deleteById(categoryId);
				return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("error", "Category not found", null, 404));
			}
		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while deleting data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public ResponseEntity<ApiResponse<List<Category>>> getCategories() {
	    List<Category> allListOfCategories = categoryRepository.findAll();
	    if (allListOfCategories!=null) {

			return ResponseEntity.ok(new ApiResponse<>("success", "Data fetched successfully", allListOfCategories, 200));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>("error", "No data found", null, 404));
		}
	}


}
