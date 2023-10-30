package com.nrt.quiz.serviceImpl;

import com.nrt.quiz.entity.Category;
import com.nrt.quiz.entity.Quiz;
import com.nrt.quiz.entity.User;
import com.nrt.quiz.entity.UserPlayedQuizHistory;
import com.nrt.quiz.repository.CategoryRepository;
import com.nrt.quiz.repository.QuizRepository;
import com.nrt.quiz.repository.UserPlayedQuizHistoryRepo;
import com.nrt.quiz.repository.UserRepository;
import com.nrt.quiz.request.QuizRequest;
import com.nrt.quiz.request.SearchPaginationRequest;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.response.UserPlayedQuizHistoryResponse;
import com.nrt.quiz.service.QuizService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	QuizRepository quizRepository;

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	UserPlayedQuizHistoryRepo userPlayedQuizHistoryRepo;
	@Autowired
	UserRepository userRepository;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public ResponseEntity<ApiResponse<Quiz>> addQuiz(QuizRequest quizRequest) {
		try {

			log.info("category id is : " + quizRequest.getCategoryId());
			long cateId = Long.parseLong(quizRequest.getCategoryId());
			Category category = categoryRepository.findById(cateId).get();

			Quiz quiz = new Quiz();
			quiz.setTitle(quizRequest.getName());
			quiz.setCategories(category);
			quiz.setMaxMarks(quizRequest.getMaxMarks());
			quiz.setDescription(quizRequest.getDescription());
			quiz.setStatus(Boolean.getBoolean(quizRequest.getActive()));
			quiz.setNumberOfQuestions(quizRequest.getNumberOfQuestions());

			Quiz payload = this.quizRepository.save(quiz);
			return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while saving data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public ResponseEntity<ApiResponse<Quiz>> updateQuiz(Long quizId, Quiz quiz) {

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
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public Quiz getQuiz(Long quizID) {
		return this.quizRepository.findById(quizID).get();
	}

	@Override
	public ResponseEntity<ApiResponse<Object>> getQuizzes(SearchPaginationRequest searchParams) {

		try {
			// Set the default value of page to 1
			Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;
			Page<Quiz> quizPage;
			// If we are using filter of AND/OR condition than start in beginning using "if"
			// condition
			quizPage = quizRepository.findAll(PageRequest.of(page - 1, 10, Sort.by(Sort.Order.desc("id"))));
			List<Quiz> quizzes = quizPage.getContent();
			Map<String, Object> map = Map.of("data", quizzes, "totalElements", quizPage.getTotalElements(),
					"currentPage", page, "perPageRecord", 10, "totalPages", quizPage.getTotalPages());
			return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", map, 200));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("An error occurred while saving data", e);
//            return e.getMessage();
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}

	}

	@Override
	public ResponseEntity<ApiResponse<Quiz>> get_Quiz(Long quizId) {
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
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public ResponseEntity<ApiResponse<?>> deleteQuiz(Long quizId) {
		try {
			Optional<Quiz> quiz = this.quizRepository.findById(quizId);
			if (quiz.isPresent()) {
				this.quizRepository.deleteById(quizId);
				return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("error", "quiz not found", null, 404));
			}
		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while deleting data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public ResponseEntity<ApiResponse<List<Quiz>>> getAllQuizzes() {
		try {
			List<Quiz> quiz = this.quizRepository.findAll();
			if (quiz != null) {
				return ResponseEntity.ok(new ApiResponse<>("success", "Quizes data fetched successfully", quiz, 200));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("Failed", "quiz dats not found", null, 404));
			}
		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while deleting data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}

	}

	@Override
	public ResponseEntity<ApiResponse<List<Quiz>>> getAllQuizzesUnderCategory(String categoryId) {
		try {
			log.info("for one category " + categoryId);

			long categoryID = Long.parseLong(categoryId);
			Category categoty = categoryRepository.findById(categoryID).get();

			List<Quiz> categotysList = quizRepository.findAllByCategories(categoty);

			if (!categotysList.isEmpty())
				return ResponseEntity
						.ok(new ApiResponse<>("success", "Quizzes data fetched successfully", categotysList, 200));
			else {
				log.info("not questions data found for this quiz id ");
				return new ResponseEntity<>(new ApiResponse<>("failed", "Quizzes not found", null, 404),
						HttpStatus.NOT_FOUND);
			}
		} catch (

		Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while deleting data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public ResponseEntity<ApiResponse<List<UserPlayedQuizHistoryResponse>>> getAttemptQuizzesList() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User userDetails = userRepository.findByEmailAddress(authentication.getName());

		List<UserPlayedQuizHistory> listOfhistory = userPlayedQuizHistoryRepo.findAllByUserId(userDetails.getId());
		log.info("GOT THE USER  :" + listOfhistory);

		List<UserPlayedQuizHistoryResponse> responseList = new ArrayList<>();
		for (UserPlayedQuizHistory his : listOfhistory) {

			UserPlayedQuizHistoryResponse userPlayedQuizHistoryResponse = new UserPlayedQuizHistoryResponse();

			log.info("the getAttemptQuiz is here :" + his.getAttemptQuizId());
			Quiz quiz = quizRepository.findById(his.getAttemptQuizId()).get();

			userPlayedQuizHistoryResponse.setAttemptQuestions(his.getAttemptQuestions());
			userPlayedQuizHistoryResponse.setAttemptQuizName(quiz.getTitle());
			userPlayedQuizHistoryResponse.setScore(his.getScore());
			userPlayedQuizHistoryResponse.setCorrectAnswers(his.getCorrectAnswers());
			userPlayedQuizHistoryResponse.setWrongAnswers(his.getWrongAnswers());

			responseList.add(userPlayedQuizHistoryResponse);

		}

		if (listOfhistory.size() != 0) {
			return ResponseEntity
					.ok(new ApiResponse<>("success", "ListOfhistory fetched successfully", responseList, 200));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>("error", "Quiz not found", null, 404));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity changeStatus(long quizId) {
		Quiz quiz = quizRepository.findById(quizId).get();
		Boolean isActive = quiz.getStatus();

		if (isActive) {
			quiz.setStatus(Boolean.FALSE);
			log.info("status changed to false");
		} else {
			quiz.setStatus(Boolean.TRUE);
		}
		quizRepository.save(quiz);
		return new ResponseEntity(HttpStatus.OK);

	}
}
