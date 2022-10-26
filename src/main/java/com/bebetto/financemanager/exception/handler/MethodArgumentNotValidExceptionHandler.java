package com.bebetto.financemanager.exception.handler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.bebetto.financemanager.response.Response;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MethodArgumentNotValidExceptionHandler {

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<Response<Map<String, Object>>> handleException(final MethodArgumentNotValidException exc,
			final WebRequest request) {
		exc.printStackTrace();
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		final List<String> errors = getErrors(exc.getFieldErrors());
		final String message = exc.getMessage();
		final Response<Map<String, Object>> response = new Response.ResponseBuilder<Map<String, Object>>()
				.setStatus(httpStatus.value()).setMessage(message).setErrors(errors).build();
		return new ResponseEntity<>(response, httpStatus);
	}

	private List<String> getErrors(final List<FieldError> fieldErrors) {
		if (fieldErrors == null) {
			return Collections.emptyList();
		}
		return fieldErrors.stream().map(fieldError -> {
			return "Field name: ['" + fieldError.getField() + "'], Rejected value: ['" + fieldError.getRejectedValue()
					+ "']";
		}).collect(Collectors.toList());
	}

}
