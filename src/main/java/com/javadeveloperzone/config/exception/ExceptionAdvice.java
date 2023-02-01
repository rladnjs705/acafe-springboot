package com.javadeveloperzone.config.exception;


import com.javadeveloperzone.model.ErrorObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity processValidationError(MethodArgumentNotValidException exception) {
		List<ErrorObject> errors = new ArrayList<>();
		for(FieldError result : exception.getBindingResult().getFieldErrors()){
			errors.add(new ErrorObject(
					result.getField(), result.getDefaultMessage()
			));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

}

