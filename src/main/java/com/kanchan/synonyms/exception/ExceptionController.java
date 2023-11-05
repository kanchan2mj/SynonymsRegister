package com.kanchan.synonyms.exception;



import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.security.core.AuthenticationException;

@ControllerAdvice
@RestController
public class ExceptionController {

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<String> handleConstraintViolation(DataIntegrityViolationException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<String> handleAll(Exception ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}