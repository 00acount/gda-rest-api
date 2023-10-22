package com.gda.restapi.app.exception;

public class ResourceForbiddenException extends RuntimeException {
	
	private static final long serialVersionUID = -4096018454101100836L;

	public ResourceForbiddenException(String message) {
		super(message);
	}
}
