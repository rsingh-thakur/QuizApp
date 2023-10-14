package com.nrt.quiz.serviceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrt.quiz.entity.User;
import com.nrt.quiz.repository.UserRepository;
import com.nrt.quiz.request.UserRequest;
import com.nrt.quiz.response.UserResponse;
import com.nrt.quiz.service.UserService;
import com.nrt.quiz.util.CommonUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	// create new user record
	@Override
	public UserResponse createUser(UserRequest userRequest) {
		return CommonUtil.decriptUser(userRepository.save(CommonUtil.encriptUserDetails(userRequest)));
	}

	// checks user exits and password is correct
	@Override
	public Boolean login(String userId, String password) {
		User user = userRepository.findByEmailAddress(CommonUtil.encrypt(userId));
		if (user != null && CommonUtil.decrypt(user.getPassword()).equalsIgnoreCase(password)) {
			return true;
		}
		return false;
	}

	// gets the all users list
	@Override
	public List<UserResponse> getAllUesrsList() {

		List<User> userList = userRepository.findAll();
		List<UserResponse> allUserList = new ArrayList<UserResponse>();

		if (allUserList != null) {
			for (User existUser : userList) {

				UserResponse user = new UserResponse();
				user = CommonUtil.decriptUser(existUser);
				if (existUser.getRole() != null)
					user.setRole(CommonUtil.decrypt(existUser.getRole().getName()));

				allUserList.add(user);
			}
		}
		log.info("users list is fetched here : " + allUserList.indexOf(0));
		return allUserList;
	}

	@Override
	public UserResponse getUserDetails(String userId) {
		log.info("user address is : " + userId);
		User user = userRepository.findByEmailAddress(CommonUtil.encrypt(userId));
		log.info("user found " + user.getAddress());
		return CommonUtil.decriptUser(user);
	}

	// updates the user details
	@Override
	public UserResponse updateUserDetails(UserRequest userRequest) {

		User existingUser = userRepository.findById(userRequest.getUserId()).get();
		log.info("user old " + existingUser.getId());
		User encriptedUserRequest = CommonUtil.encriptUserDetails(userRequest);
		existingUser.setAddress(encriptedUserRequest.getAddress());
		existingUser.setCreationDate(encriptedUserRequest.getCreationDate());
		existingUser.setEmailAddress(encriptedUserRequest.getEmailAddress());
		existingUser.setFirstName(encriptedUserRequest.getFirstName());
		existingUser.setImagePath(encriptedUserRequest.getImagePath());
		existingUser.setLastName(encriptedUserRequest.getLastName());
		existingUser.setPhone(encriptedUserRequest.getPhone());
		existingUser.setUserType(encriptedUserRequest.getUserType());

		User user = userRepository.save(existingUser);
		log.info("user new " + user.getId());
		return CommonUtil.decriptUser(user);
	}

	@Override
	public UserResponse getUserDetails(long userId) {
		log.info("user address is : " + userId);
		User user = userRepository.findById(userId).get();
		if (user != null) {
			log.info("user found " + user.getAddress());
			return CommonUtil.decriptUser(user);
		}
		return null;

	}

	// delete the user record
	@Override
	public Boolean deleteUserRecord(long userId) {
		try {
			userRepository.deleteById(userId);
			return Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

}
