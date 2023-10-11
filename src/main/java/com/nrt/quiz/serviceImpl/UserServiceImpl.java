package com.nrt.quiz.serviceImpl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nrt.quiz.entity.User;
import com.nrt.quiz.repository.UserRepository;
import com.nrt.quiz.request.UserRequest;
import com.nrt.quiz.service.UserService;
import com.nrt.quiz.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

  //create new user records
	@SuppressWarnings("deprecation")
	@Override
	public User createUser(UserRequest userRequst, MultipartFile file) {

		User user = new User();

		user.setFirstName(CommonUtil.encrypt(userRequst.getFirstName()));
		user.setLastName(CommonUtil.encrypt(userRequst.getFirstName()));
		user.setImagePath(CommonUtil.encrypt(file.getOriginalFilename()));
		user.setEmailAddress(CommonUtil.encrypt(userRequst.getEmailAddress()));
		user.setPassword(CommonUtil.encrypt(userRequst.getPassword()));
		user.setUserType(CommonUtil.encrypt(userRequst.getUserType()));
		user.setCreationDate(new Date(new java.util.Date().getDate()));
		//CommonUtil.saveFile(file);
		return userRepository.save(user);
	}

  //checks user exits and password is correct
	@Override
	public Boolean login(String userId, String password) {

		User user = userRepository.findByEmailAddress(CommonUtil.encrypt(userId));
		if (user != null && CommonUtil.decrypt(user.getPassword()).equalsIgnoreCase(password)) {
			return true;
		}
		return false;
	}

}
