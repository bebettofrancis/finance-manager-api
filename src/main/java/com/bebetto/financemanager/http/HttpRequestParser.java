package com.bebetto.financemanager.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestParser {

	private final HttpServletRequest httpServletRequest;
	private Map<String, List<String>> headersMap;
	private Map<String, List<String>> parametersMap;

	public HttpRequestParser(final HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public Map<String, List<String>> getHeadersMap() {
		if (this.headersMap == null) {
			this.headersMap = new HashMap<>();
			final Enumeration<String> headerNames = this.httpServletRequest.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				final String headerName = headerNames.nextElement();
				this.headersMap.putIfAbsent(headerName, new ArrayList<>());
				final List<String> headerValues = this.headersMap.get(headerName);
				final Enumeration<String> headerValuesTmp = this.httpServletRequest.getHeaders(headerName);
				while (headerValuesTmp.hasMoreElements()) {
					headerValues.add(headerValuesTmp.nextElement());
				}
			}
		}
		return this.headersMap;
	}

	public Map<String, List<String>> getParametersMap() {
		if (this.parametersMap == null) {
			this.parametersMap = this.httpServletRequest.getParameterMap().entrySet().stream().collect(
					Collectors.toMap(Map.Entry::getKey, value -> Arrays.asList(value.getValue()), null, HashMap::new));
		}
		return this.parametersMap;
	}

	public void setHeadersMap(final Map<String, List<String>> headersMap) {
		this.headersMap = headersMap;
	}

	public void setParametersMap(final Map<String, List<String>> parametersMap) {
		this.parametersMap = parametersMap;
	}

}
