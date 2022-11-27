package com.bebetto.financemanager.exception.handler;

import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bebetto.financemanager.exception.ExpenseNotFoundException;
import com.bebetto.financemanager.logger.LoggingManager;
import com.bebetto.financemanager.response.Response;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExpenseNotFoundExceptionHandler {

	@ExceptionHandler(value = { ExpenseNotFoundException.class })
	public ResponseEntity<Response<Map<String, Object>>> handleExpenseNotFoundException(
			final ExpenseNotFoundException exc) {
		final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		String message = exc.getMessage();
		if (message == null) {
			message = Response.DEFAULT_ERROR_MESSAGE;
		}
		LoggingManager.warn(message);
		final Response<Map<String, Object>> response = new Response.ResponseBuilder<Map<String, Object>>()
				.setStatus(httpStatus.value()).setMessage(message).build();
		return new ResponseEntity<>(response, httpStatus);
	}

}
