package com.nrt.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrt.quiz.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
