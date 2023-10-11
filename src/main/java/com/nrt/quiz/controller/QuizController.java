package com.nrt.quiz.controller;


//import lombok.extern.log4j.Log4j;
import com.nrt.quiz.entity.Quiz;
import com.nrt.quiz.request.SearchPaginationRequest;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.service.QuizService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping()
    public ResponseEntity<ApiResponse<Quiz>> addQuiz(@RequestBody Quiz quiz){
//        return ResponseEntity.ok(this.quizService.addQuiz(quizEntity));
        return this.quizService.addQuiz(quiz);
    }

    //update category
    @PutMapping("/{quizId}")
    public ResponseEntity<ApiResponse<Quiz>> updateQuiz(@PathVariable("quizId") Long quizId , @RequestBody Quiz quiz){
        return this.quizService.updateQuiz(quizId, quiz);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getQuizzes(@RequestBody SearchPaginationRequest searchParams){
        return this.quizService.getQuizzes(searchParams);
    }

    // get single category
    @GetMapping("/{quizId}")
    public ResponseEntity<ApiResponse<Quiz>>  getQuiz(@PathVariable("quizId") Long quizId){
        return this.quizService.get_Quiz(quizId);
    }
    @DeleteMapping("/{quizId}")
    public ResponseEntity<ApiResponse<?>> deleteQuiz(@PathVariable("quizId") Long quizId){
        return this.quizService.deleteQuiz(quizId);
    }

}
