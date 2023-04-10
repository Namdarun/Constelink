package com.srp.constelinkfile.Exception;

import org.springframework.http.HttpStatus;

import com.google.api.Http;

public enum CustomExceptionType {
	RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E301", "잘못된 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E302", "서버 오류 입니다."),
	GCS_FILE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "E303", "파일 업로드 도중 오류가 발생했습니다"),
	FILE_TYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "E304", "JPG, JPEG, PNG 타입의 파일만 업로드 가능합니다");

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