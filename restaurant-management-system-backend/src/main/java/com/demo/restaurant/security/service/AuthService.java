package com.demo.restaurant.security.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.demo.restaurant.security.conf.JwtTokenUtil;
import com.demo.restaurant.security.model.JwtRequest;
import com.demo.restaurant.security.model.JwtResponse;
import com.demo.restaurant.util.ResponseMessage;

@Service
public class AuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public JwtResponse authenticateUser(JwtRequest authenticationRequest) throws Exception {
		// start check if username is null or password is null
		if (Objects.isNull(authenticationRequest.getUsername()) || authenticationRequest.getUsername().isEmpty()) {
			return new JwtResponse(null, ResponseMessage.NOT_VALID_USERNAME);
		}
		if (Objects.isNull(authenticationRequest.getPassword()) || authenticationRequest.getUsername().isEmpty()) {
			return new JwtResponse(null, ResponseMessage.NOT_VALID_PASSWORD);
		}

		LOGGER.debug("----->start login for username : {}", authenticationRequest.getUsername());

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		String token = jwtTokenUtil.generateToken(userDetails);

		return new JwtResponse(token, ResponseMessage.USER_LOGIN_SUCCESSFULLY);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
