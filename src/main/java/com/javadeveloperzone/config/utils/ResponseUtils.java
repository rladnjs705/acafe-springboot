package com.javadeveloperzone.config.utils;

import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.model.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResponseUtils {

	public static ResponseEntity<ResponseVo> response(Map<String, Object> respMap){
		return new ResponseEntity<ResponseVo>(new ResponseVo(respMap), HttpStatus.OK);
	}

	/**
	 * exception response
	 */
	public static ResponseEntity<ResponseVo> response(ResultCodeType type, String msg){
		ResponseVo responseVo = null;
		if(null != msg) {
			responseVo = new ResponseVo(type, msg);
		}else {
			responseVo = new ResponseVo(type, type.getMsg());
		}
		return new ResponseEntity<ResponseVo>(responseVo, type.getStatus());
	}

}
