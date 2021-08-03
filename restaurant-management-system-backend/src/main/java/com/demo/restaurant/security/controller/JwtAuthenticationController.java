package com.demo.restaurant.security.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.restaurant.security.model.JwtRequest;
import com.demo.restaurant.security.model.JwtResponse;
import com.demo.restaurant.security.service.AuthService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthService authService;

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationController.class);

	@PostMapping(value = "/auth/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		JwtResponse jwtResponse = authService.authenticateUser(authenticationRequest);

		BodyBuilder response = null;

		// return bad request response if username or password is null
		if (Objects.isNull(jwtResponse.getToken())) {
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
		}

		// return http status ok if user login successfully
		if (Objects.nonNull(jwtResponse.getToken())) {
			response = ResponseEntity.status(HttpStatus.OK);
		}
		return response.body(jwtResponse);

	}

}
