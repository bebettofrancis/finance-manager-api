package com.bebetto.financemanager.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingManager.class);

	public static void debug(final Object value) {
		LOGGER.debug("{}", value);
	}

	public static void error(final Object value) {
		LOGGER.error("{}", value);
	}

	public static void error(final Object value, final Throwable t) {
		LOGGER.error("{}", value, t);
	}

	public static void info(final Object value) {
		LOGGER.info("{}", value);
	}

	public static void info(final Object value1, final Object value2) {
		LOGGER.info("{} {}", value1, value2);
	}

	public static void trace(final Object value) {
		LOGGER.trace("{}", value);
	}

	public static void warn(final Object value) {
		LOGGER.warn("{}", value);
	}

	public static void warn(final Object value, final Throwable t) {
		LOGGER.warn("{}", value, t);
	}

	private LoggingManager() {
		super();
	}

}
