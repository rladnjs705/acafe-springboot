package com.javadeveloperzone.config.exception;


import com.javadeveloperzone.config.utils.ResponseUtils;
import com.javadeveloperzone.model.ErrorResult;
import com.javadeveloperzone.model.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity processValidationError(MethodArgumentNotValidException exception) {
		log.error("MethodArgumentNotValidException e {}", exception.getMessage());

		return ResponseUtils.response(ResultCodeType.ERROR_PARAM, exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseVo> processValidationError(IllegalArgumentException exception) {
		log.error("IllegalArgumentException e {}", exception.getMessage());

		return ResponseUtils.response(ResultCodeType.ERROR_PARAM, exception.getMessage());
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ResponseVo> processValidationError(IllegalStateException exception) {
		log.error("IllegalStateException e {}", exception.getMessage());

		return ResponseUtils.response(ResultCodeType.ERROR_PARAM, exception.getMessage());
	}

}

