package com.gda.restapi.app.exception;

public class BadCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 2898619916033185663L;
	
	public BadCredentialsException(String message) {
		super(message);
	}

}
