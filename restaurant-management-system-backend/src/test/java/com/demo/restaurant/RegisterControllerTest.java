package com.demo.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.restaurant.controller.RegisterController;
import com.demo.restaurant.dto.UserDto;
import com.demo.restaurant.entity.User;
import com.demo.restaurant.repository.UserRepository;
import com.demo.restaurant.service.RegisterService;
import com.demo.restaurant.util.ResponseMessage;

class RegisterControllerTest {

	@Autowired
	RegisterController registerController;

	@InjectMocks
	RegisterService registerService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	private void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testRegister() {
		UserDto existUser = new UserDto("ahmed", "ahmed", "1111", "010111111111");

		when(userRepository.findByUsername(existUser.getUsername()))
				.thenReturn(new User(existUser.getName(), existUser.getUsername(), existUser.getPassword()));

		assertEquals(ResponseMessage.DUBLICATE_USERNAME,
				registerService.registerUser(existUser).getBody().getDescription());

	}

}
