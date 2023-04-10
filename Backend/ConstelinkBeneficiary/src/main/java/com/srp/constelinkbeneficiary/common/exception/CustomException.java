package com.srp.constelinkbeneficiary.common.exception;

public class CustomException extends RuntimeException {
	private final com.srp.constelinkbeneficiary.common.exception.CustomExceptionType exception;

	public CustomException(com.srp.constelinkbeneficiary.common.exception.CustomExceptionType exception) {
		super(exception.getMessage());
		this.exception = exception;
	}

	public com.srp.constelinkbeneficiary.common.exception.CustomExceptionType getException() {
		return exception;
	}
}