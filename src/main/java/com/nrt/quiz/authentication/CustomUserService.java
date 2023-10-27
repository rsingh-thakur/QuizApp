package com.nrt.quiz.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.nrt.quiz.entity.User;
import com.nrt.quiz.exception.DeactivatedUserException;
import com.nrt.quiz.repository.UserRepository;
import com.nrt.quiz.serviceImpl.PermissionService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CustomUserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PermissionService  permissionService ;

	@Override
	public CustomUserDetails loadUserByUsername(String email) throws DeactivatedUserException{

		User user = userRepository.findByEmailAddress(email);
		log.info("user is present"+user);
		if (user!=null) {
			log.debug("user is present");
			if (user.getStatus() == 1) {
				log.debug("status of user is :" + user.getStatus());
				return new CustomUserDetails(user, permissionService);
			} else {
				log.error("DeactivatedUserException is thrown");
				throw new DeactivatedUserException("User is Disabled please reactivate this ");
			}

		} else {
			log.info("user is not found");
			log.error("BadCredentialsException is thrown");
			throw new BadCredentialsException("Bad credentials, User not found with email: " + email);
		}
			
	}

}