package com.nrt.quiz.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nrt.quiz.entity.Role;

public interface RoleService {

	 public Role saveRole (Role role);
	 
	 public List<Role> getAllRoles();
	 
	 public Role getRoleById(Long id);
	 
	 public void deleteRole(Long id);
	 
}
