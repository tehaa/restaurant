package com.demo.restaurant.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.restaurant.dto.UserDto;
import com.demo.restaurant.service.RegisterService;
import com.demo.restaurant.util.ResponseMessage;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
public class RegisterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private RegisterService registerService;

	@ApiOperation(value = "This Api is responsible for add a new user in the system ")
	@RequestMapping(value = "register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ResponseMessage> register(@Valid  @RequestBody UserDto userDto) {
		
		LOGGER.debug("start /api register user");
		
		return registerService.registerUser(userDto) ;
		
	}

}
