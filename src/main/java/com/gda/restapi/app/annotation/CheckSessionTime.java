package com.gda.restapi.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gda.restapi.app.model.SessionTime;
import com.gda.restapi.app.validation.checkSessionTimeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = checkSessionTimeValidator.class)
public @interface CheckSessionTime {
	Class<? extends SessionTime> enumClass() default SessionTime.class;
	String message();
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
