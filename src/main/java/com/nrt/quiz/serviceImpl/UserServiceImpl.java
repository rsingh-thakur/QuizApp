package com.nrt.quiz.serviceImpl;

import java.sql.Date;
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

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	// create new user records
	@SuppressWarnings("deprecation")
	@Override
	public UserResponse createUser(UserRequest userRequst) {

		User user = new User();

		user.setFirstName(CommonUtil.encrypt(userRequst.getFirstName()));
		user.setLastName(CommonUtil.encrypt(userRequst.getFirstName()));
		user.setEmailAddress(CommonUtil.encrypt(userRequst.getEmailAddress()));
		user.setPassword(CommonUtil.encrypt(userRequst.getPassword()));
		user.setUserType(CommonUtil.encrypt(userRequst.getUserType()));
		user.setCreationDate(new Date(new java.util.Date().getDate()));

		User createdUser = userRepository.save(user);
		UserResponse response = new UserResponse();
		if (createdUser != null) {

			response.setFirstName(CommonUtil.decrypt(createdUser.getFirstName()));
			response.setLastName(CommonUtil.decrypt(createdUser.getLastName()));
			response.setEmailAddress(CommonUtil.decrypt(createdUser.getEmailAddress()));
			response.setUserType(CommonUtil.decrypt(createdUser.getUserType()));
			response.setUesrId(createdUser.getId());
			response.setCreated_At((createdUser.getCreationDate()));
			if (createdUser.getRole() != null)
				response.setRole(CommonUtil.decrypt(createdUser.getRole().getName()));

		}
		return response;
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

	@Override
	public List<UserResponse> getAllUesrsList() {
		List<User> userList = userRepository.findAll();

		List<UserResponse> allUserList = new ArrayList<>();
		if (allUserList != null) {
			for (User existUser : userList) {
				UserResponse user = new UserResponse();
				user.setFirstName(CommonUtil.decrypt(existUser.getFirstName()));
				user.setLastName(CommonUtil.decrypt(existUser.getLastName()));
				user.setEmailAddress(CommonUtil.decrypt(existUser.getEmailAddress()));
				user.setUserType(CommonUtil.decrypt(existUser.getUserType()));
				user.setUesrId(existUser.getId());
				user.setCreated_At((existUser.getCreationDate()));
				if (existUser.getRole() != null)
					user.setRole(CommonUtil.decrypt(existUser.getRole().getName()));
				allUserList.add(user);

			}
		}
		return allUserList;
	}

	@Override
	public UserResponse getUserDetails(String userId) {
		User user = userRepository.findByEmailAddress(userId);
		UserResponse response = new UserResponse();
		if(user!=null) {
		
		response.setFirstName(CommonUtil.decrypt(user.getFirstName()));
		response.setLastName(CommonUtil.decrypt(user.getLastName()));
		response.setEmailAddress(CommonUtil.decrypt(user.getEmailAddress()));
		response.setUserType(CommonUtil.decrypt(user.getUserType()));
		response.setUesrId(user.getId());
		response.setCreated_At((user.getCreationDate()));
		if (user.getRole() != null)
			response.setRole(CommonUtil.decrypt(user.getRole().getName()));
		}
	return response;
}

}
