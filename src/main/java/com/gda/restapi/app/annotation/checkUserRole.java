package com.gda.restapi.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gda.restapi.app.validation.checkUserRoleValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = checkUserRoleValidator.class)
public @interface checkUserRole {
	String message() default "";
	String regexp();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
