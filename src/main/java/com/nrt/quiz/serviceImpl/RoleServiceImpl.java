package com.nrt.quiz.serviceImpl;

import java.util.List;

import com.nrt.quiz.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrt.quiz.entity.Role;
import com.nrt.quiz.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role saveRole(Role role) {
		try {
			return roleRepository.save(role);

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	public List<Role> getAllRoles() {
		try {
			return roleRepository.findAll();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	public Role getRoleById(Long id) {
		try {
			return roleRepository.findById(id).orElse(null);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	public void deleteRole(Long id) {
		try {
			roleRepository.deleteById(id);
		} catch (Exception e) {
			System.out.println(e);

		}

	}

}