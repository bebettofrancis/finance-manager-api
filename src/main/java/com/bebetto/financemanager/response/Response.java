package com.bebetto.financemanager.response;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Response<T> {

	public static final String DEFAULT_ERROR_MESSAGE = "Something went wrong...!";
	public static final String DEFAULT_SUCCESS_MESSAGE = "Request processed successfully...!";

	private int status;
	private String message;
	private T data;
	private String timeStamp;
	private List<String> errors;

	public Response() {
		super();
	}

	public Response(final int status, final String message, final T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.timeStamp = getFormattedTimeStamp();
	}

	public T getData() {
		return this.data;
	}

	public List<String> getErrors() {
		return this.errors;
	}

	private String getFormattedTimeStamp() {
		return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
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
