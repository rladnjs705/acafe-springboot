package com.javadeveloperzone.config.exception;

import org.springframework.http.HttpStatus;

public enum ResultCodeType {
	SUCCESS(0, HttpStatus.OK, "success"),
	ERROR_AUTH(1000, HttpStatus.UNAUTHORIZED, "auth error"),
	ERROR_USER_NOT_FOUND(1001, HttpStatus.UNAUTHORIZED, "user not found"),
	ERROR_PWD_NOT_MATCH(1002, HttpStatus.UNAUTHORIZED, "pwd not match"),
	ERROR_AUTH_EMAIL(1003, HttpStatus.UNAUTHORIZED, "이메일 또는 패스워드 오류입니다."),
	ERROR_AUTH_DUPLICATE(1003, HttpStatus.UNAUTHORIZED, "이미 생성된 아이디 입니다."),
	ERROR_PARAM(1100, HttpStatus.BAD_REQUEST, "parameter error"),
	ERROR_REST(1200, HttpStatus.BAD_REQUEST, "rest error"),
	ERROR_EXCEL(2000, HttpStatus.INTERNAL_SERVER_ERROR, "excel error"),
	ERROR_EXCEPTION(9000, HttpStatus.INTERNAL_SERVER_ERROR, "internal server error");

	private int code;
	private HttpStatus status;
	private String msg;

	private ResultCodeType(
			int inputCode, HttpStatus inputHttpStatus, String inputMsg) {
		code = inputCode;
		status = inputHttpStatus;
		msg = inputMsg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
