package com.nrt.quiz.repository;

import com.nrt.quiz.entity.Category;
import com.nrt.quiz.entity.Quiz;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // in below function we have used "containing" in end so this will run "like" query while matching character/alphabets
    public Page<Quiz> findByTitleContaining(String name, Pageable pageable);

    //    in below, the spelling of category should be similar at that mentioned in entity
    public Page<Quiz> findByCategories(Category category, Pageable pageable);

    public Page<Quiz> findByStatus(Boolean Status, Pageable pageable);

    //we can use AND/OR and it will run custom search querry on its own
    public Page<Quiz> findByCategoriesAndStatus(Category category, Boolean Status, Pageable pageable);

	public List<Quiz> findAllByCategories(Category categoty);

}
