package com.revature.exceptions;

/**
 * Thrown if user does not have permission to perform the operation
 */
public class UnauthorizedException extends RuntimeException {

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "You do not have permission to perform this operation.";
	}

}
