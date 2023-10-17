package com.nrt.quiz.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nrt.quiz.entity.Questions;
import com.nrt.quiz.repository.QuestionRepository;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.QuestionService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public ResponseEntity<ApiResponse<Questions>> addQuestion(Questions question) {
		try {
			Questions saveQues = questionRepository.save(question);
			if (saveQues != null)
				return ResponseEntity
						.ok(new ApiResponse<Questions>("success", "Question data Added successfully", saveQues, 200));
			else
				return ResponseEntity.internalServerError()
						.body(new ApiResponse<Questions>("failed", "failed to save this data", null, 500));

		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while deleting data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}

	}

	@Override
	public ResponseEntity<ApiResponse<Questions>> updateQuestion(Long questionId, Questions question) {
		try {
			Questions existingQuestion = questionRepository.findById(questionId).get();
			if (existingQuestion != null)
			{
				existingQuestion.setOptionA(question.getOptionA());
				existingQuestion.setOptionB(question.getOptionB());
				existingQuestion.setOptionC(question.getOptionC());
				existingQuestion.setOptionD(question.getOptionD());
			
				existingQuestion.setAnswer(question.getAnswer());
				existingQuestion.setQuestion(question.getQuestion());
				//questionRepository.save(existingQuestion
				
				return ResponseEntity
						.ok(new ApiResponse<>("success", "Question data Added successfully", questionRepository.save(existingQuestion), 200));
			}
			else
				return ResponseEntity.internalServerError()
						.body(new ApiResponse<>("failed", "failed to save this data", null, 500));

		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while deleting data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public ResponseEntity<ApiResponse<List<Questions>>> getAllQuestionzes() {
		try {
			List<Questions> QuesList = questionRepository.findAll();
			if (QuesList != null)
				return ResponseEntity
						.ok(new ApiResponse<>("success", "Question data fetched successfully", QuesList, 200));
			else
				return ResponseEntity.internalServerError()
						.body(new ApiResponse<>("failed", "failed get the data list", null, 500));

		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while deleting data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public ResponseEntity<ApiResponse<Questions>> getQuestion(Long questionId) {
		try {
			Questions Question = questionRepository.findById(questionId).get();
			if (Question != null)
				return ResponseEntity
						.ok(new ApiResponse<>("success", "Question data Added successfully", Question, 200));
			else
				return ResponseEntity.internalServerError()
						.body(new ApiResponse<>("failed", "failed to save this data", null, 500));

		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while deleting data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	@Override
	public ResponseEntity<ApiResponse<?>> deleteQuestion(Long questionId) {
		try {
			questionRepository.deleteById(questionId);
			return ResponseEntity.ok(new ApiResponse<>("success", "Question data Detedted successfully", null, 200));

		} catch (

		Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while deleting data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}

	}

}
