package com.bebetto.financemanager.response;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Response<T> {

	public static final String DEFAULT_MESSAGE = "Request processed successfully...!";

	private int status;
	private String message;
	private T data;
	private String timeStamp;

	public Response() {
		super();
	}

	public Response(final int status, final String message, final T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		final ZonedDateTime zonedDateTime = ZonedDateTime.now();
		this.timeStamp = zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	public T getData() {
		return this.data;
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
