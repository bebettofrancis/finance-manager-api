package com.bebetto.financemanager.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bebetto.financemanager.logger.LoggingManager;

public class DateValidator implements ConstraintValidator<DateConstraint, String> {

	private String pattern;
	private LocalDate maxDate;
	private LocalDate minDate;
	private boolean dateRangeValidate;

	@Override
	public void initialize(final DateConstraint dateConstraint) {
		this.pattern = dateConstraint.pattern();
		this.maxDate = LocalDate.now().plusDays(dateConstraint.maxDate());
		this.minDate = LocalDate.now().minusDays(dateConstraint.minDate());
		this.dateRangeValidate = dateConstraint.dateRangeValidate();
	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		boolean validDate = false;
		try {
			final LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ofPattern(this.pattern));
			LoggingManager.info("Expense date", localDate);
			if (!this.dateRangeValidate || (!localDate.isBefore(this.minDate) && !localDate.isAfter(this.maxDate))) {
				validDate = true;
			}
		} catch (final NullPointerException | DateTimeParseException exc) {
			LoggingManager.info(exc);
		}
		return validDate;
	}

}
