package com.srp.authserver.common.exception;

import org.springframework.http.HttpStatus;

public enum CustomExceptionType {
	RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E501", "잘못된 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E502", "서버 오류 입니다."),
	NULL_HEADER_EXCEPTION(HttpStatus.UNAUTHORIZED, "E503", "헤더가 존재하지 않습니다."),
	NULL_TOKEN_EXCEPTION(HttpStatus.NOT_FOUND, "E504", "토큰이 존재하지 않습니다"),
	JWT_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E505", "JWT 토큰이 만료됐습니다"),
	JWT_TYPE_EXCEPTION(HttpStatus.UNAUTHORIZED, "E506", "JWT 토큰 타입이 다릅니다."),
	JWT_ROLE_EXCEPTION(HttpStatus.FORBIDDEN, "E507", "해당 요청에 대한 권한이 존재하지 않습니다"),
	JWT_SIGNATURE_EXCEPTION(HttpStatus.BAD_REQUEST, "E508", "잘못 생성되 JWT 토큰입니다");

	private final HttpStatus httpStatus;
	private final String code;
	private String message;

	CustomExceptionType(HttpStatus httpStatus, String code) {
		this.httpStatus = httpStatus;
		this.code = code;
	}

	CustomExceptionType(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}