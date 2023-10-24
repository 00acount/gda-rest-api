package com.gda.restapi.app.exception;

public class ConflictedResourceException extends RuntimeException {

	private static final long serialVersionUID = -773930015549091635L;
	
	public ConflictedResourceException(String message) {
		super(message);
	}

}
