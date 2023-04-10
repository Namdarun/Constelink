package com.srp.constelinkmember.common.exception;

import org.springframework.http.HttpStatus;

public enum CustomExceptionType {
	RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E001", "잘못된 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "서버 오류 입니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E003", "사용자 정보가 존재하지 않습니다."),
	OAUTH2_AUTHENTICATION_PROCESSING_EXCEPTION(HttpStatus.BAD_REQUEST, "E004", "지원되지 않는 소셜 타입입니다"),
	ABNORMAL_ACCESS_EXCEPTION(HttpStatus.BAD_REQUEST, "E005", "당신은 해커 입니까?"),
	NULL_TOKEN_EXCEPTION(HttpStatus.NOT_FOUND, "E006", "토큰이 존재하지 않습니다"),
	NOT_LOGINED_EXCEPTION(HttpStatus.BAD_REQUEST, "E007", "로그인을 진행한 멤버가 아닙니다"),
	JWT_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E008", "JWT 토큰 만료됐습니다");

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