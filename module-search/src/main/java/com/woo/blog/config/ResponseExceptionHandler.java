package com.woo.blog.config;

import feign.FeignException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
@RequiredArgsConstructor
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@Getter
	public static class ExceptionModel {

	}

	/**
	 * Http Status 500 Error
	 * @param ex the exception
	 * @param request the current request
	 * @return ErrorDetails
	 * @Date 2022-10-13 18:00:00
	 */
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, WebRequest request) {

		log.error("Error Occurred By Unknown Exception : ", ex);

		return new ResponseEntity<>(new ExceptionModel(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 *  API 파라미터 에러가 발생한 경우
	 *
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return ResponseEntity
	 */
	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.info("Error Occurred By Bad Request Exception : ", ex);

		return new ResponseEntity<>(new ExceptionModel(), status);
	}

	/**
	 * Feign Exception
	 * @param ex the exception
	 * @param request the current request
	 * @return ErrorDetails
	 * @Date 2022-10-13 18:00:00
	 */
	@ExceptionHandler({FeignException.class})
	protected ResponseEntity<Object> handleExceptionInternal(FeignException ex, WebRequest request) {

		log.error("Error Occurred By Feign Exception : ", ex);

		return new ResponseEntity<>(new ExceptionModel(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
