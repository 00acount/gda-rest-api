package com.gda.restapi.app.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gda.restapi.app.model.ErrorResponse;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException
														(ResourceNotFoundException ex) {

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setError("Resource Not Found");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now().withNano(0));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	//////////////////////////////////////////////////////////////////////////////////////

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponse> handleBindException
														(BindException ex) {
		
		BindingResult result = ex.getBindingResult();
        String errorMessage = result.getFieldError().getDefaultMessage();

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setError("Bad Request");
		errorResponse.setMessage(errorMessage);
		errorResponse.setTimestamp(LocalDateTime.now().withNano(0));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////

	@ExceptionHandler(InvalidIdFormatException.class)
	public ResponseEntity<ErrorResponse> handleInvalidIdFormatException
														(InvalidIdFormatException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setError("Bad Request");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now().withNano(0));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	//////////////////////////////////////////////////////////////////////////////////////

	@ExceptionHandler(ResourceForbiddenException.class)
	public ResponseEntity<ErrorResponse> handleResourceForbiddenException
														(ResourceForbiddenException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
		errorResponse.setError("Forbidden Request");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now().withNano(0));
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
	}

	//////////////////////////////////////////////////////////////////////////////////////

	@ExceptionHandler(ConflictedResourceException.class)
	public ResponseEntity<ErrorResponse> handleConflictedResourceException
														(ConflictedResourceException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setStatus(HttpStatus.CONFLICT.value());
		errorResponse.setError("Conflicted Request");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now().withNano(0));
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException
														(BadCredentialsException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setError("Bad Credentials");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now().withNano(0));
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	}

}
