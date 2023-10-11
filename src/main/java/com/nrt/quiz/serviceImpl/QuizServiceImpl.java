package com.nrt.quiz.serviceImpl;

import com.nrt.quiz.entity.Category;
import com.nrt.quiz.entity.Quiz;
import com.nrt.quiz.repository.QuizRepository;
import com.nrt.quiz.request.SearchPaginationRequest;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.QuizService;
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
public class QuizServiceImpl implements QuizService {
    @Autowired
    QuizRepository quizRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Quiz>> addQuiz(Quiz quiz) {
        try {
            Quiz payload = this.quizRepository.save(quiz);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Quiz>> updateQuiz(Long quizId,Quiz quiz) {

        try {
            Optional<Quiz> existingCategory = this.quizRepository.findById(quizId);

            if (existingCategory.isPresent()) {
                // Update the existing category with the new data
                Quiz updatedQuiz = existingCategory.get();
                updatedQuiz.setTitle(quiz.getTitle());
                updatedQuiz.setDescription(quiz.getDescription());
                // Save the updated category
                Quiz payload = this.quizRepository.save(updatedQuiz);

                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "Quiz not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public Quiz getQuiz(Long quizID) {
        return this.quizRepository.findById(quizID).get();
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getQuizzes(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String name = searchParams.getName();
            Long categoryId = searchParams.getCategoryId();
            Boolean active = searchParams.getActive();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Quiz> quizPage;

            //  If we are using filter of AND/OR condition than start in beginning using "if" condition
            if (categoryId != null && active != null) {
                Category category= new Category();
                category.setId(categoryId);
                quizPage = quizRepository.findByCategoriesAndActive(category,active, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("qid"))));
            }
            else if (id != null) {
                Optional<Quiz> categoryOptional = quizRepository.findById(id);
                if (categoryOptional.isPresent()) {
                    Quiz category = categoryOptional.get();
                    quizPage = new PageImpl<>(Collections.singletonList(category));
                } else {
                    quizPage = Page.empty(); // No matching category found
                }
            }
            else if (name != null) {
                quizPage = quizRepository.findByTitleContaining(name, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("qid"))));
            }
            else if (categoryId != null) {
                Category category= new Category();
                category.setId(categoryId);
                quizPage = quizRepository.findByCategories(category, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("qid"))));
            }else if (active != null) {
                quizPage = quizRepository.findByActive(active, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("qid"))));
            }
            else {
                quizPage = quizRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("qid"))));
            }

            List<Quiz> quizzes = quizPage.getContent();

            Map<String, Object> map = Map.of(
                    "data", quizzes,
                    "totalElements", quizPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", quizPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<Quiz>> get_Quiz(Long quizId){
        try {
            Optional<Quiz> quizOptional = this.quizRepository.findById(quizId);

            if (quizOptional.isPresent()) {
                Quiz quiz = quizOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", quiz, 200));
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
    public ResponseEntity<ApiResponse<?>> deleteQuiz(Long quizId) {
        try {
            Optional<Quiz> quiz = this.quizRepository.findById(quizId);
            if (quiz.isPresent()) {
                //        QuizEntity quizEntity = new QuizEntity();
                //        quizEntity.setQid(qId);
                //        this.quizRepository.delete(quizEntity);
                // below one is easy single line code

                this.quizRepository.deleteById(quizId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "quiz not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
}
