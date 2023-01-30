package com.javadeveloperzone.config.exception;

public class ParamException extends Exception{

	private static final long serialVersionUID = -4662672193354957246L;

	private String msg;

	public ParamException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
