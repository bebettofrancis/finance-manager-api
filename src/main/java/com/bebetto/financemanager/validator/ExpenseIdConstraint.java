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
@Constraint(validatedBy = ExpenseIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpenseIdConstraint {

	Class<?>[] groups() default {};

	String message() default "Invalid expense id...!";

	Class<? extends Payload>[] payload() default {};

}
