package com.revature.exceptions;

/**
 * Should be thrown when a user tries to login with invalid credentials
 */
public class InvalidCredentialsException extends RuntimeException {

	private static final long serialVersionUID = -6573307333524845568L;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "User credentials not recongized.";
	}

}
