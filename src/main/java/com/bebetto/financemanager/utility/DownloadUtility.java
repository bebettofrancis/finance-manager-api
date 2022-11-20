package com.bebetto.financemanager.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class DownloadUtility {

	public static HttpHeaders getHttpHeadersForDownload(final ByteArrayResource byteArrayResource) {
		final HttpHeaders headers = new HttpHeaders();
		final String fileName = Optional.ofNullable(byteArrayResource.getFilename())
				.orElseGet(() -> LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
		headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE));
		headers.setContentLength(byteArrayResource.contentLength());
		return headers;
	}

	private DownloadUtility() {
		super();
	}

}
