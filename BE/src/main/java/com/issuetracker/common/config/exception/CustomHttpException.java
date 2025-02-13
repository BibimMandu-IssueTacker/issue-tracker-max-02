package com.issuetracker.common.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomHttpException extends RuntimeException {

	private final ErrorType errorType;

	public HttpStatus getHttpStatus() {
		return errorType.getStatus();
	}

	public String getMessage() {
		return errorType.getMessage();
	}
}
