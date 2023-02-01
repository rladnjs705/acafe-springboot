package com.javadeveloperzone.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//@ControllerAdvice(annotations = Controller.class)
//public class WebExceptionAdvice {
//	private static final Logger logger = LoggerFactory.getLogger(WebExceptionAdvice.class);
//
//	/**
//	 * 알수없는 페이지 오류
//	 */
//	@ExceptionHandler(Exception.class)
//    public String commonException(Exception e, HttpServletRequest req, WebRequest request) {
//		logger.error("WebExceptionAdvice commonException: {}", e);
//		return "common/404";
//    }
//
//}
