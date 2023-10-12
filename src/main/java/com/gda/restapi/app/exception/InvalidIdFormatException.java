package com.gda.restapi.app.exception;

public class InvalidIdFormatException extends RuntimeException {
	
	private static final long serialVersionUID = -2135638594287241920L;

	public InvalidIdFormatException(String message) {
		super(message);
	}
}
