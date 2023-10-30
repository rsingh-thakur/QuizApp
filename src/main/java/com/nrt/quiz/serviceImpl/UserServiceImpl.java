package com.nrt.quiz.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nrt.quiz.authentication.CustomUserDetails;
import com.nrt.quiz.authentication.CustomUserService;
import com.nrt.quiz.authentication.JwtUtil;
import com.nrt.quiz.entity.User;
import com.nrt.quiz.exception.DeactivatedUserException;
import com.nrt.quiz.repository.RoleRepository;
import com.nrt.quiz.repository.UserRepository;
import com.nrt.quiz.request.LoginRequest;
import com.nrt.quiz.request.UserRequest;
import com.nrt.quiz.response.ApiResponse;
import com.nrt.quiz.response.LoginResponse;
import com.nrt.quiz.response.UserResponse;
import com.nrt.quiz.service.UserService;
import com.nrt.quiz.util.CommonUtil;

import lombok.extern.log4j.Log4j2;

@Configuration
@Service
@Log4j2
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserService userDetailsService;

	// create new user record
	@Override
	public UserResponse createUser(UserRequest userRequest) {

		String password = userRequest.getPassword();

		BCryptPasswordEncoder encoder = this.bCryptPasswordEncoder();

		String encodedPassword = encoder.encode(password);

		userRequest.setPassword(encodedPassword);

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
	@SuppressWarnings("unlikely-arg-type")
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
	public UserResponse getUserDetails() {
		log.info("users data is fetched here : " + CommonUtil.getCurrentUserDetails());
		return CommonUtil.getCurrentUserDetails();
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

	@Override
	public ResponseEntity<ApiResponse<LoginResponse>> generateToken(LoginRequest loginRequest) {
		String token = null;
		User user = null;

		log.info("request data : " + loginRequest.toString());
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			CustomUserDetails userDetails = this.userDetailsService.loadUserByUsername(loginRequest.getEmail());
			token = jwtUtil.generateToken(userDetails);
			user = userRepository.findByEmailAddress(loginRequest.getEmail());
			log.info("user found ....");
			return ResponseEntity.ok(new ApiResponse<LoginResponse>("success", "token fetched",
					new LoginResponse(token, user.getCreationDate()), 200));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("exception thrown Exception handled ");
			if (e instanceof DeactivatedUserException) {
				log.error("DeactivatedUserException thrown DeactivatedUserException handled ");
				return ResponseEntity
						.ofNullable(new ApiResponse<LoginResponse>("Failed", "token not generated", null, 444));
			}

		}
		return ResponseEntity.ofNullable(new ApiResponse<LoginResponse>("Failed", "token not generated", null, 444));

	}

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public ResponseEntity<String> changeStatus(long userID) {
		User user = userRepository.findById(userID).get();
		int isActive = user.getStatus();

		if (isActive == 1) {
			user.setStatus(0);
			log.info("status changed to false");
		} else {
			user.setStatus(1);
		}
		userRepository.save(user);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
