package com.springboot.application.custom.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Component to implement global exception handling and customize the response
 * based on the exception type.
 * 
 * @author Waqas
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handle DataNotFound Exception. @ExceptionHandler(DataNotFoundException.class)
	 * indicates that this method would handle exceptions of the
	 * DataNotFoundException type.
	 * 
	 * @param dnfe
	 * @param request
	 * @return
	 */
	@ExceptionHandler(DataNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleDataNotFoundException(final DataNotFoundException dnfe,
			final WebRequest request) {

		log.error("DataNotFoundException occurred in Rest Controller, Error message : {}", dnfe.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(new Date(), dnfe.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handle Exception. @ExceptionHandler(Exception.class) indicates that this
	 * method would handle exceptions of the Exception type.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleException(DataNotFoundException ex, WebRequest request) {
		log.error("Unknown Error occurred in Rest Controller, Error message : {}", ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}