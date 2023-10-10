package com.nrt.quiz.repository;

import com.nrt.quiz.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    // in below function we have used "containing" in end so this will run "like" query while matching character/alphabets
    public Page<Category> findByTitleContaining(String name, Pageable pageable);
}
