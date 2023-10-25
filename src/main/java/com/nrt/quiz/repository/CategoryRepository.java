package com.nrt.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrt.quiz.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    // in below function we have used "containing" in end so this will run "like" query while matching character/alphabets
    //public Page<Category> findByTitleContaining(String name, Pageable pageable);
    List<Category> findAll();
}
