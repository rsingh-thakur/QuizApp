package com.nrt.quiz.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nrt.quiz.entity.Quiz;
import com.nrt.quiz.entity.User;
import com.nrt.quiz.entity.UserPlayedQuizHistory;
import com.nrt.quiz.repository.QuizRepository;
import com.nrt.quiz.repository.UserPlayedQuizHistoryRepo;
import com.nrt.quiz.repository.UserRepository;
import com.nrt.quiz.request.UserPlayedQuizHistoryReq;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.UserPlayedQuizHistoryService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserPlayedQuizHistoryServiceImpl implements UserPlayedQuizHistoryService {

	@Autowired
	UserPlayedQuizHistoryRepo historyRepo;

	@Autowired
	QuizRepository quizRepository;

	@Autowired
	UserRepository repository;

	@Override
	public ResponseEntity<ApiResponse<UserPlayedQuizHistory>> addUserQuizHistory(
			UserPlayedQuizHistoryReq quizHistoryRequest) {

		String userId = null;

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		if (attr != null) {
			HttpSession session = attr.getRequest().getSession();
			userId = (String) session.getAttribute("email");
		} else {
			log.info("falied to get the current session");
		}

		User user = null;
		if (userId != null)
			user = repository.findByEmailAddress(userId);

		long quizId = Long.parseLong(quizHistoryRequest.getAttemptQuiz());

		try {

			log.info("category id is : " + quizHistoryRequest.getAttemptQuiz());
			Quiz quiz = quizRepository.findById(quizId).get();

			quiz.getMaxMarks();
			int markPerQuestions = changeToInt(quiz.getNumberOfQuestions()) / changeToInt(quiz.getMaxMarks());

			int attemptQues = changeToInt(quizHistoryRequest.getAttemptQuestions());

			int correctAnswers = changeToInt(quizHistoryRequest.getCorrectAnswers());

			int nq = changeToInt(quiz.getNumberOfQuestions());

			UserPlayedQuizHistory history = new UserPlayedQuizHistory();
			history.setAttemptQuestions(attemptQues);
			history.setAttemptQuiz(quiz);
			history.setCorrectAnswers(correctAnswers);
			history.setScore(markPerQuestions * correctAnswers);
			history.setWrongAnswers(nq - correctAnswers);
			history.setUser(user);

			UserPlayedQuizHistory payload = this.historyRepo.save(history);
			return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
		} catch (Exception e) {
			// Handle the exception here and log it
			log.error("An error occurred while saving data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}

	public int changeToInt(String str) {
		if (str != null)
			return Integer.parseInt(str);

		return 3;
	}

	@Override
	public ResponseEntity<ApiResponse<List<UserPlayedQuizHistory>>> getUserQuizHistory() {
		try {
			List<UserPlayedQuizHistory> userPlayedQuizHistoryList = historyRepo.findAll();

			return ResponseEntity
					.ok(new ApiResponse<>("success", "Data saved successfully", userPlayedQuizHistoryList, 200));
		} catch (Exception e) {
			log.error("An error occurred while saving data", e);
			return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
		}
	}
}
