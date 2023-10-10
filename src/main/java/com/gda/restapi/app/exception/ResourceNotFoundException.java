package com.gda.restapi.app.exception;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 3287803084173855349L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
