package com.demo.restaurant.security.model;

public class JwtResponse {
	private static final long serialVersionUID = -8091879091924046844L;

	private final String jwttoken;

	private String description;

	public JwtResponse(String jwttoken, String description) {
		super();
		this.jwttoken = jwttoken;
		this.description = description;
	}

	
	public String getToken() {
		return this.jwttoken;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
}
