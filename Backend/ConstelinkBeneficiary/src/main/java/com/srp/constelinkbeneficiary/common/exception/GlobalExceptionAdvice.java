package com.srp.constelinkbeneficiary.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.srp.constelinkbeneficiary.db.dto.response.ExceptionResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
	//예외처리
	@ExceptionHandler(value = com.srp.constelinkbeneficiary.common.exception.CustomException.class)
	public ResponseEntity<ExceptionResponse> dongeunHandler(
		com.srp.constelinkbeneficiary.common.exception.CustomException e) {
		return getResponseEntity(e.getException());
	}

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ExceptionResponse> runtimeExceptionHandler(RuntimeException e) {
		log.info(e.getMessage());
		return getResponseEntity(com.srp.constelinkbeneficiary.common.exception.CustomExceptionType.RUNTIME_EXCEPTION);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> exceptionHandler(Exception e) {
		log.info(e.getMessage());
		return getResponseEntity(
			com.srp.constelinkbeneficiary.common.exception.CustomExceptionType.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ExceptionResponse> getResponseEntity(
		com.srp.constelinkbeneficiary.common.exception.CustomExceptionType exceptionType) {
		return ResponseEntity
			.status(exceptionType.getHttpStatus())
			.body(ExceptionResponse.builder()
				.code(exceptionType.getCode())
				.message(exceptionType.getMessage())
				.build());
	}
}
