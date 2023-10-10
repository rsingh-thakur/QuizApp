package com.nrt.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrt.quiz.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
