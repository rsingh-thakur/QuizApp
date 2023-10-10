package com.nrt.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrt.quiz.entity.User;

public interface userRepository extends JpaRepository<User, Long>{

}
