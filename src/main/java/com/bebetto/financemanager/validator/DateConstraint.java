package com.bebetto.financemanager.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Constraint(validatedBy = DateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {

	Class<?>[] groups() default {};

	long maxDate() default 0;

	String message() default "Invalid date/time format...!";

	long minDate() default 0;

	String pattern();

	Class<? extends Payload>[] payload() default {};

	boolean validateDateRange() default false;

}
