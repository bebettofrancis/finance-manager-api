package com.bebetto.financemanager.exception.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.bebetto.financemanager.response.Response;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<Response<Map<String, Object>>> handleException(final MethodArgumentNotValidException exc,
			final WebRequest request) {
		exc.printStackTrace();
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		final Response<Map<String, Object>> response = new Response<>(httpStatus.value(), exc.getMessage(), null);
		return new ResponseEntity<>(response, httpStatus);
	}

}
