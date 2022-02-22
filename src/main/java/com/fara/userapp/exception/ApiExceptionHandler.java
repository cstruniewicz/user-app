package com.fara.userapp.exception;

import com.fara.userapp.dto.ApiErrorDto;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<ApiErrorDto> handleException(HttpServletRequest req, MethodArgumentNotValidException exception) {
		return getCommonExceptionResponse(req,
				new UserDataConstraintViolationException("User data incorrect format, lengths or values")
				, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BaseCrudException.class)
	ResponseEntity<ApiErrorDto> handleException(HttpServletRequest req, BaseCrudException exception) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ResponseStatus responseStatus;
		if ((responseStatus = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class)) != null) {
			status = responseStatus.code();
		}
		return getCommonExceptionResponse(req, exception, status);
	}


	@ExceptionHandler(HttpMessageNotReadableException.class)
	ResponseEntity<ApiErrorDto> handleMsgNotReadableException(HttpServletRequest req
			, HttpMessageNotReadableException exception) {
			if (exception.getLocalizedMessage().contains("com.fara.userapp.enums")) {
				return getCommonExceptionResponse(req,
						new UserDataConstraintViolationException("Incorrect user state or rule set")
						, HttpStatus.BAD_REQUEST);
			}
			return getCommonExceptionResponse(req, exception, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	ResponseEntity<ApiErrorDto> handleException(HttpServletRequest req, EmptyResultDataAccessException exception) {
		return getCommonExceptionResponse(req, exception, HttpStatus.BAD_REQUEST);
	}


	private ResponseEntity<ApiErrorDto> getCommonExceptionResponse(HttpServletRequest req, Throwable exception
			, HttpStatus status) {
		return new ResponseEntity<>(ApiErrorDto.builder()
				.type(exception.getClass().getSimpleName())
				.title(exception.getLocalizedMessage())
				.resourceLocation(req.getRequestURL().toString())
				.build(),
				status);
	}


}
