package com.challenge.inventory.VO;

public class AuthResponse {
	private final String token;

	public String getToken() {
	return token;
	}


	public AuthResponse(String token) {
		super();
		this.token = token;
	}

}
