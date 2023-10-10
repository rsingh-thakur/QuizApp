package com.nrt.quiz.serviceImpl;

import com.nrt.quiz.entity.Category;
import com.nrt.quiz.repository.CategoryRepository;
import com.nrt.quiz.request.SearchPaginationRequest;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<Object>> getCategories(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String name = searchParams.getName();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Category> categoryPage;

            if (id != null) {
                Optional<Category> categoryOptional = categoryRepository.findById(id);
                if (categoryOptional.isPresent()) {
                    Category category = categoryOptional.get();
                    categoryPage = new PageImpl<>(Collections.singletonList(category));
                } else {
                    categoryPage = Page.empty(); // No matching category found
                }
            }
            else if (name != null) {
                categoryPage = categoryRepository.findByTitleContaining(name, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                categoryPage = categoryRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<Category> categories = categoryPage.getContent();

            Map<String, Object> map = Map.of(
                    "data", categories,
                    "totalElements", categoryPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", categoryPage.getTotalPages()
            );
            return ResponseEntity.ok( new ApiResponse<>("success", "Data retrieved successfully", map, 200));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An error occurred while saving data", e);
//            return e.getMessage();
            return ResponseEntity.internalServerError().body( new ApiResponse<>("error", e.getMessage(), null, 500));
        }

    }

    @Override
    public ResponseEntity<ApiResponse<Category>> getCategory(Long categoryId) {
        try {
            Optional<Category> categoryOptional = this.categoryRepository.findById(categoryId);

            if (categoryOptional.isPresent()) {
                Category categoryEntity = categoryOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", categoryEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "Category not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<?>> deleteCategory(Long categoryId) {
        try {
            Optional<Category> category = this.categoryRepository.findById(categoryId);
            if (category.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);

                // below one is easy single line code

                this.categoryRepository.deleteById(categoryId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "Category not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
}
