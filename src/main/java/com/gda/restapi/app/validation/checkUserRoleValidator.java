package com.gda.restapi.app.validation;

import java.util.regex.Pattern;

import com.gda.restapi.app.annotation.checkUserRole;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class checkUserRoleValidator implements ConstraintValidator<checkUserRole, String> {
	
	private Pattern pattern;

	@Override
	public void initialize(checkUserRole enumPattern) {
		this.pattern = Pattern.compile(enumPattern.regexp());
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = value != null && pattern.matcher(value).matches();
		return isValid;
	}
}
