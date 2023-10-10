package com.nrt.quiz.serviceImpl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nrt.quiz.entity.User;
import com.nrt.quiz.repository.UserRepository;
import com.nrt.quiz.request.UserRequest;
import com.nrt.quiz.service.UserService;

@Service
public class userServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@SuppressWarnings("deprecation")
	@Override
	public User createUser(UserRequest userRequst, MultipartFile file) {

		User user = new User();
		user.setFirstName(userRequst.getFirstName());
		user.setLastName(userRequst.getFirstName());
		user.setImagePath(file.getOriginalFilename());
		user.setEmailAddress(userRequst.getEmailAddress());
		user.setPassword(userRequst.getPassword());
		user.setUserType(userRequst.getUserType());
		user.setCreationDate(new Date(new java.util.Date().getDate()));
        
		return userRepository.save(user);
	}

	@Override
	public Boolean login(String userId, String password) {

		User user = userRepository.findByEmailAddress(userId);

		if (user != null && user.getPassword().equalsIgnoreCase(password)) {
			return true;
		}

		return false;
	}

}
