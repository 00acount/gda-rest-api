package com.gda.restapi.app.exception;

public class NoContentException extends RuntimeException {

	private static final long serialVersionUID = -1566474823928038051L;

	public NoContentException(String message) {
		super(message);
	}
}
