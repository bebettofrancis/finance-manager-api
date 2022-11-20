package com.bebetto.financemanager.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.bebetto.financemanager.http.HttpRequestParser;
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
		final HttpRequestParser httpRequestParser = new HttpRequestParser(httpServletRequest);
		LoggingManager.info(getRequestData(httpRequestParser));
		chain.doFilter(request, response);
		final long endTime = System.currentTimeMillis();
		LoggingManager.info("Processing ended, Total time taken: " + (endTime - startTime) + " ms...!");
		MDC.clear();
	}

	private String getHeaders(final HttpRequestParser httpRequestParser) {
		final StringBuilder headers = new StringBuilder(CommonUtility.padRightSpaces("Request Headers:", 25));
		headers.append(httpRequestParser.getHeadersMap());
		return headers.toString();
	}

	private String getIpAddress(final HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteAddr();
	}

	private String getParameters(final HttpRequestParser httpRequestParser) {
		final StringBuilder parameters = new StringBuilder(CommonUtility.padRightSpaces("Request Parameters:", 25));
		parameters.append(httpRequestParser.getParametersMap());
		return parameters.toString();
	}

	private String getRequestData(final HttpRequestParser httpRequestParser) {
		final StringBuilder requestData = new StringBuilder("\n##########REQUEST DATA START##########\n\n");
		requestData.append(getHeaders(httpRequestParser)).append("\n\n");
		requestData.append(getParameters(httpRequestParser)).append("\n\n");
		requestData.append("##########REQUEST DATA END  ##########");
		return requestData.toString();
	}

}
