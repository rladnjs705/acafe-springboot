package com.javadeveloperzone.config.exception;


import com.javadeveloperzone.config.utils.ResponseUtils;
import com.javadeveloperzone.model.ResponseVo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

import javax.security.sasl.AuthenticationException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

	/**
	 * 인증오류
	 */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ResponseVo> handleAuthenticationException(AuthenticationException e, HttpServletRequest req, WebRequest request) {
    	logger.error("Authentication Exception: {}", e);

        return ResponseUtils.response(ResultCodeType.ERROR_AUTH, null);
    }

    /**
     * 파라미터 오류
     */
    @ExceptionHandler(value = ParamException.class)
    public ResponseEntity<ResponseVo> handleInvalidParameterException(ParamException e, HttpServletRequest req, WebRequest request) {
    	logger.error("ParamException Exception: {}", e);

        return ResponseUtils.response(ResultCodeType.ERROR_PARAM, e.getMsg());
    }

	/**
	 * 외부연동 오류
	 */
	@ExceptionHandler({RestClientException.class, HttpClientErrorException.class})
    public ResponseEntity<ResponseVo> restTempleteException(Exception e, HttpServletRequest req, WebRequest request) {
		logger.error("restTempleteException Exception: {}", e);

		return ResponseUtils.response(ResultCodeType.ERROR_REST, null);
    }

	/**
	 * 알수없는 오류
	 */
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVo> commonException(Exception e, HttpServletRequest req, WebRequest request) {
		logger.error("commonException Exception: {}", e);

		return ResponseUtils.response(ResultCodeType.ERROR_EXCEPTION, null);
    }

}
