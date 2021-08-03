package com.demo.restaurant.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import com.demo.restaurant.util.ResponseMessage;

public class UserDto {

	@NonNull
	@Size(min = 2, message = ResponseMessage.NOT_VALID_USERNAME)
	private String username;

	@NotNull
	@Size(min = 2, message = ResponseMessage.NOT_VALID_USERNAME)
	private String name;

	@NonNull
	@Size(min = 2, message = ResponseMessage.NOT_VALID_PASSWORD)
	private String password;

	@NonNull
	@Size(min = 2, message = ResponseMessage.NOT_VALID_PHONE, max = 15)
	private String mobileNumber;
	
	
	

	public UserDto(@Size(min = 2, message = "enter valid Email") String username,
			@NotNull @Size(min = 2, message = "enter valid Email") String name,
			@Size(min = 2, message = "enter valid password") String password,
			@Size(min = 2, message = "enter valid phone", max = 15) String mobileNumber) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.mobileNumber = mobileNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "UserDto [username=" + username + ", name=" + name + ", password=" + password + ", mobileNumber="
				+ mobileNumber + "]";
	}

}
