package com.gda.restapi.app.validation;

import java.util.List;
import java.util.stream.Stream;

import com.gda.restapi.app.annotation.CheckSessionTime;
import com.gda.restapi.app.model.SessionTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class checkSessionTimeValidator implements ConstraintValidator<CheckSessionTime, String> {
	
	private List<String> acceptedValues;
	

	@Override
	public void initialize(CheckSessionTime constraintAnnotation) {
		acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
								.map(SessionTime::getTime).toList();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value == null || acceptedValues.contains(value);
	}

}
