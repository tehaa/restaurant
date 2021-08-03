package com.demo.restaurant.util;

public enum UserRole {

	ADMIN("admin"), USER("user");

	private String Role;

	private UserRole(String role) {
		Role = role;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

}
