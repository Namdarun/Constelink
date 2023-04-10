package com.srp.constelinkbeneficiary.common.exception;

import org.springframework.http.HttpStatus;

public enum CustomExceptionType {
	RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E101", "잘못된 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E102", "서버 오류 입니다."),
	HOSPITAL_NOT_FOUND(HttpStatus.NOT_FOUND, "E103", "해당 ID 병원 정보가 존재하지 않습니다."),
	BENEFICIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "E104", "해당 ID 수혜자 정보가 존재하지 않습니다."),
	RECOVERYDIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "E105", "해당 ID 일기 정보가 존재하지 않습니다."),
	AUTHORIZATION_ERROR(HttpStatus.FORBIDDEN, "E106", "권한이 없습니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "E107", "사용자 ID 정보가 존재하지 않습니다."),
	HOSPITAL_AUTHORIZATION_ERROR(HttpStatus.FORBIDDEN, "E108", "다른 병원 환자입니다. 권한이 없습니다."),
	TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "E109", "병원 토큰이 없습니다.")
	;

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