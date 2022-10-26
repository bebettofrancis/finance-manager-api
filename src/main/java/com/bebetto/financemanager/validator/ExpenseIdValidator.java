package com.bebetto.financemanager.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExpenseIdValidator implements ConstraintValidator<ExpenseIdConstraint, Integer> {

	@Override
	public boolean isValid(final Integer value, final ConstraintValidatorContext context) {
		return value > 0;
	}

}
