package com.bebetto.financemanager.filter;

import java.io.IOException;
import java.util.Enumeration;

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
		LoggingManager.info(getHeaders(httpServletRequest));
		chain.doFilter(request, response);
		final long endTime = System.currentTimeMillis();
		LoggingManager.info("Processing ended, Total time taken: " + (endTime - startTime) + " ms...!");
		MDC.clear();
	}

	private String getHeaders(final HttpServletRequest httpServletRequest) {
		final StringBuilder headersValue = new StringBuilder("\n##########REQUEST HEADERS START##########\n");
		final Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			final String headerName = headerNames.nextElement();
			headersValue.append("Header name: [" + headerName + "]");
			final Enumeration<String> headers = httpServletRequest.getHeaders(headerName);
			while (headers.hasMoreElements()) {
				final String header = headers.nextElement();
				headersValue.append(" Header value: [" + header + "]");
			}
			headersValue.append("\n");
		}
		headersValue.append("##########REQUEST HEADERS END  ##########");
		return headersValue.toString();
	}

	private String getIpAddress(final HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteAddr();
	}

}
