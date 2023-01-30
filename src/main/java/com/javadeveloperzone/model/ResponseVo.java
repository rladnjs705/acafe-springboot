package com.javadeveloperzone.model;


import com.javadeveloperzone.config.exception.ResultCodeType;

import java.util.HashMap;
import java.util.Map;

public class ResponseVo {
	private int resultCode = ResultCodeType.SUCCESS.getCode();

	private String resultMsg = ResultCodeType.SUCCESS.getMsg();

	private Map<String, Object> data = new HashMap<String, Object>();

	public ResponseVo() {

	}

	public ResponseVo(ResultCodeType type) {
		resultCode = type.getCode();
		resultMsg = type.getMsg();
	}

	public ResponseVo(ResultCodeType type, String msg) {
		resultCode = type.getCode();
		resultMsg = msg;
	}

	public ResponseVo(int rCode, String rMsg, Map<String, Object> respMap) {
		resultCode = rCode;
		resultMsg = rMsg;
		data.putAll(respMap);
	}

	public ResponseVo(Map<String, Object> respMap) {
		data.putAll(respMap);
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		//data까지 로깅해야되는 경우 String str = "resultCode: " + resultCode + " resultMsg: " + resultMsg + " data:" + data.toString();
		String str = "resultCode: " + resultCode + " resultMsg: " + resultMsg;
		return str;
	}

}
