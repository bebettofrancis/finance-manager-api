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
		MDC.put("transactionId", CommonUtility.getUniqueId());
		MDC.put("requestURI", requestUri);
		MDC.put("requestMethod", requestMethod);
		LoggingManager.info("Processing started...!");
		chain.doFilter(request, response);
		final long endTime = System.currentTimeMillis();
		LoggingManager.info("Processing ended, Total time taken: " + (endTime - startTime) + " ms...!");
		MDC.clear();
	}

}
