package com.demo.restaurant.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.restaurant.dto.UserDto;
import com.demo.restaurant.entity.User;
import com.demo.restaurant.repository.UserRepository;
import com.demo.restaurant.util.ResponseMessage;
import com.demo.restaurant.util.UserRole;

@Service
public class RegisterService {

	@Autowired
	private UserRepository userRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterService.class);

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender javaMailSender ;

	/**
	 * 
	 * @param userDto
	 * @return
	 */
	public ResponseEntity<ResponseMessage> registerUser(UserDto userDto) {

		LOGGER.debug("----->start register user : {} to our system", userDto.getName());
		try {

			LOGGER.debug("----->start checking if user is registered with the same username or not");

			if (Objects.isNull(userRepository.findByUsername(userDto.getUsername()))) {

				LOGGER.debug("----->No username is registered for user : {}", userDto.getName());

				User user = new User(userDto.getName(), userDto.getUsername(), userDto.getMobileNumber());
								
				user.setPassword(passwordEncoder.encode(userDto.getPassword()));

				user.setRole(UserRole.USER);
				
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(user.getUsername());
				mailMessage.setSubject("Confirmation");
				mailMessage.setText("Hello Dear Welcome To My site ");

				javaMailSender.send(mailMessage);

				userRepository.save(user);
				
				LOGGER.debug("----->user with username: {} registered successfully",userDto.getUsername());

				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(ResponseMessage.USER_REGISTERED_SUCCESSFULLY));

			} else {
				LOGGER.error("----->this email with user : {}  was registered before", userDto.getName());

				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseMessage(ResponseMessage.DUBLICATE_USERNAME));
			}
		} catch (Exception e) {
			LOGGER.error("-----> Error while adding user : {}",userDto.getUsername(),e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(ResponseMessage.ERROR_WHILE_ADDING_DATA));

		}
	}

}
