package com.nrt.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrt.quiz.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmailAddress(String emailAddress);

}
