package com.company.issuetracker.client.repository;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

/* Unused */
public class RestErrorHandler implements ResponseErrorHandler {

	private static final Logger log = LoggerFactory.getLogger(RestErrorHandler.class);

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		log.error("ResponseEntity error: {} {}", response.getStatusCode(), response.getStatusText());
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return isError(response.getStatusCode());
	}

	private boolean isError(HttpStatus status) {
		HttpStatus.Series series = status.series();
		return (HttpStatus.Series.CLIENT_ERROR.equals(series)
				|| HttpStatus.Series.SERVER_ERROR.equals(series));
	}
}