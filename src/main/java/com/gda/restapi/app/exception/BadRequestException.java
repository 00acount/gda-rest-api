package com.gda.restapi.app.exception;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1318671463323523016L;

	public BadRequestException(String message) {
		super(message);
	}
}
