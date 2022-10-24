package com.bebetto.financemanager.exception;

public class ExpenseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExpenseNotFoundException() {
		super();
	}

	public ExpenseNotFoundException(final String message) {
		super(message);
	}

	public ExpenseNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ExpenseNotFoundException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExpenseNotFoundException(final Throwable cause) {
		super(cause);
	}

}
