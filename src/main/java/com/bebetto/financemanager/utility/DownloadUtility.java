package com.bebetto.financemanager.utility;

import java.net.URLConnection;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class DownloadUtility {

	public static final HttpHeaders getHttpHeadersForDownload(final ByteArrayResource byteArrayResource) {
		final HttpHeaders headers = new HttpHeaders();
		final String fileName = Optional.ofNullable(byteArrayResource.getFilename())
				.orElseGet(CommonUtility::getCurrentDateTimeString);
		headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
		headers.setContentType(getMediaType(fileName));
		headers.setContentLength(byteArrayResource.contentLength());
		return headers;
	}

	public static final MediaType getMediaType(final String fileName) {
		final String mediaType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
		return Optional.ofNullable(mediaType).map(MediaType::parseMediaType).orElse(MediaType.APPLICATION_OCTET_STREAM);
	}

	private DownloadUtility() {
		super();
	}

}
