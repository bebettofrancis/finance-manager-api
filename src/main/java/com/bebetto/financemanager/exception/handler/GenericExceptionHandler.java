package com.bebetto.financemanager.exception.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bebetto.financemanager.logger.LoggingManager;
import com.bebetto.financemanager.response.Response;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GenericExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Response<Map<String, Object>>> handleException(final Exception exc,
			final HttpServletRequest httpServletRequest) {
		LoggingManager.warn(exc.getClass().getName(), exc);
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		String message = exc.getMessage();
		if (message == null) {
			message = Response.DEFAULT_ERROR_MESSAGE;
		}
		final Response<Map<String, Object>> response = new Response.ResponseBuilder<Map<String, Object>>()
				.setStatus(httpStatus.value()).setMessage(message).build();
		return new ResponseEntity<>(response, httpStatus);
	}

}
