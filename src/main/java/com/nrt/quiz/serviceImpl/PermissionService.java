package com.nrt.quiz.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrt.quiz.entity.Permission;
import com.nrt.quiz.entity.Role;
import com.nrt.quiz.entity.User;
import com.nrt.quiz.exception.CantDeleteException;
import com.nrt.quiz.repository.PermissionRepository;

import jakarta.transaction.Transactional;

@Service
public class PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	com.nrt.quiz.repository.UserRepository userRepository;

	@Transactional
	public void deleteRolesByUserId(long userId) {
		try {
			permissionRepository.deleteRolesByUserId(userId);
		} catch (Exception e) {
			throw new CantDeleteException("Permission can not be deleted..! Cause> " + e.getMessage());
		}
		permissionRepository.deleteRolesByUserId(userId);
	}

	
	public List<String> getAllPermissionsList(long userId) {
		List<Permission> AllpermissionsList = permissionRepository.findByRoleId(userId);
		List<String> permissionList = new ArrayList<String>();
		for (Permission permissions : AllpermissionsList) {
			permissionList.add(permissions.getName());
		}
		return permissionList;
	}

	
	public List<String> getPermissionsByUserId(int i) {
		List<String> permissions = new ArrayList<>();

		User user = userRepository.findById((long) i).get();
		if (user != null) {
			// Get the user's role
			Role role = user.getRole();
			if (role != null) {
				// Get the permissions associated with the role
				List<Permission> rolePermissions = role.getPermission();
				for (Permission permission : rolePermissions) {
					permissions.add(permission.getName());
				}
			}
		}
		return permissions;
	}
}