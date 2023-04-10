package com.srp.constelinkfundraising.common.exception;

import org.springframework.http.HttpStatus;

public enum CustomExceptionType {
	RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E201", "잘못된 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E202", "서버 오류 입니다."),
	BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "E203", "북마크 id가 0이하거나 존재하지 않습니다."),
	CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "E204", "카테고리 id가 0이하거나 존재하지 않습니다."),
	FUNDRAISING_NOT_FOUND(HttpStatus.NOT_FOUND, "E205", "모금 id가 0이하거나 존재하지 않습니다."),
	DONATION_MONEY_ERROR(HttpStatus.NOT_ACCEPTABLE, "E206", "기부 금액은 0원 이상이어야합니다."),
	GOAL_AMOUNT_ERROR(HttpStatus.NOT_ACCEPTABLE, "E207", "모금 목표금액은 0원 이상이어야 합니다."),
	BENEFICIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "E208", "수혜자 id값이 0이하거나 못찾습니다."),
	TITLE_NOT_FOUND(HttpStatus.NOT_FOUND, "E209", "제목이 빈칸입니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "E210", "사용자 Id가 0이하거나 찾이 못찾습니다."),
	HOSPITAL_NOT_FOUND(HttpStatus.NOT_FOUND, "E211", "병원 Id가 0이하거나 찾이 못찾습니다."),
	TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "E212", "토큰을 못 찾았습니다. 로그인해주세요")

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