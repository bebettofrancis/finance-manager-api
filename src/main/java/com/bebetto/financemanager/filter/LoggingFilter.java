package com.bebetto.financemanager.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.bebetto.financemanager.logger.LoggingManager;
import com.bebetto.financemanager.utility.CommonUtility;

@Component
public class LoggingFilter implements Filter {

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final long startTime = System.currentTimeMillis();
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		final String requestUri = httpServletRequest.getRequestURI();
		final String requestMethod = httpServletRequest.getMethod();
		final String transactionId = CommonUtility.getUniqueId();
		final String ipAddress = getIpAddress(httpServletRequest);
		MDC.put("ipAddress", ipAddress);
		MDC.put("transactionId", transactionId);
		MDC.put("requestURI", requestUri);
		MDC.put("requestMethod", requestMethod);
		LoggingManager.info("Processing started...!");
		LoggingManager.info(getRequestData(httpServletRequest));
		chain.doFilter(request, response);
		final long endTime = System.currentTimeMillis();
		LoggingManager.info("Processing ended, Total time taken: " + (endTime - startTime) + " ms...!");
		MDC.clear();
	}

	private String getHeaders(final HttpServletRequest httpServletRequest) {
		final StringBuilder headers = new StringBuilder(CommonUtility.padRightSpaces("Request Headers:", 25));
		Map<String, List<String>> headersMap = null;
		final Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			if (headersMap == null) {
				headersMap = new HashMap<>();
			}
			final String headerName = headerNames.nextElement();
			headersMap.putIfAbsent(headerName, new ArrayList<>());
			final List<String> headerValues = headersMap.get(headerName);
			final Enumeration<String> headerValuesTmp = httpServletRequest.getHeaders(headerName);
			while (headerValuesTmp.hasMoreElements()) {
				headerValues.add(headerValuesTmp.nextElement());
			}
		}
		headers.append(headersMap);
		return headers.toString();
	}

	private String getIpAddress(final HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteAddr();
	}

	private String getParameters(final HttpServletRequest httpServletRequest) {
		final StringBuilder parameters = new StringBuilder(CommonUtility.padRightSpaces("Request Parameters:", 25));
		final Map<String, String[]> parametersMap = httpServletRequest.getParameterMap();
		parameters.append(parametersMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
				value -> Arrays.asList(value.getValue()), (k1, k2) -> k2, HashMap::new)));
		return parameters.toString();
	}

	private String getRequestData(final HttpServletRequest httpServletRequest) {
		final StringBuilder requestData = new StringBuilder("\n##########REQUEST DATA START##########\n\n");
		requestData.append(getHeaders(httpServletRequest)).append("\n\n");
		requestData.append(getParameters(httpServletRequest)).append("\n\n");
		requestData.append("##########REQUEST DATA END  ##########");
		return requestData.toString();
	}

}
