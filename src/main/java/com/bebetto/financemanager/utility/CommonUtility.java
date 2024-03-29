package com.bebetto.financemanager.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CommonUtility {

	public static String getCurrentDateTimeString() {
		return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	public static String getUniqueId() {
		return UUID.randomUUID().toString();
	}

	public static boolean isEmpty(final Collection<?> collection) {
		return Objects.isNull(collection) || collection.isEmpty();
	}

	public static boolean isEmpty(final Map<?, ?> map) {
		return Objects.isNull(map) || map.isEmpty();
	}

	public static String padRightSpaces(final String value, final int length) {
		Objects.requireNonNull(value);
		if (value.length() > length) {
			return value;
		}
		final String format = "%1$-" + length + "s";
		return String.format(format, value);
	}

	private CommonUtility() {
		super();
	}

}
