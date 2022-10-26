package com.bebetto.financemanager.response;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class Response<T> {

	public static class ResponseBuilder<T> {

		private int status;
		private String message;
		private T data;
		private String timeStamp;
		private List<String> errors;

		public ResponseBuilder() {
			super();
		}

		public Response<T> build() {
			final Response<T> response = new Response<>();
			response.setData(getData());
			response.setErrors(getErrors());
			response.setMessage(getMessage());
			response.setStatus(getStatus());
			response.setTimeStamp(getTimeStamp());
			return response;
		}

		public T getData() {
			return this.data;
		}

		public List<String> getErrors() {
			return this.errors == null ? Collections.emptyList() : this.errors;
		}

		private String getFormattedTimeStamp() {
			return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		}

		public String getMessage() {
			return this.message == null ? Response.DEFAULT_SUCCESS_MESSAGE : this.message;
		}

		public int getStatus() {
			return this.status == 0 ? Response.DEFAULT_STATUS_CODE : this.status;
		}

		public String getTimeStamp() {
			return this.timeStamp == null ? getFormattedTimeStamp() : this.timeStamp;
		}

		public ResponseBuilder<T> setData(final T data) {
			this.data = data;
			return this;
		}

		public ResponseBuilder<T> setErrors(final List<String> errors) {
			this.errors = errors;
			return this;
		}

		public ResponseBuilder<T> setMessage(final String message) {
			this.message = message;
			return this;
		}

		public ResponseBuilder<T> setStatus(final int status) {
			this.status = status;
			return this;
		}

		public ResponseBuilder<T> setTimeStamp(final String timeStamp) {
			this.timeStamp = timeStamp;
			return this;
		}

	}

	public static final String DEFAULT_ERROR_MESSAGE = "Something went wrong...!";
	public static final String DEFAULT_SUCCESS_MESSAGE = "Request processed successfully...!";
	public static final int DEFAULT_STATUS_CODE = 202;

	private int status;
	private String message;
	private T data;
	private String timeStamp;
	private List<String> errors;

	public Response() {
		super();
	}

	public T getData() {
		return this.data;
	}

	public List<String> getErrors() {
		return this.errors;
	}

	public String getMessage() {
		return this.message;
	}

	public int getStatus() {
		return this.status;
	}

	public String getTimeStamp() {
		return this.timeStamp;
	}

	public void setData(final T data) {
		this.data = data;
	}

	public void setErrors(final List<String> errors) {
		this.errors = errors;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public void setTimeStamp(final String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
