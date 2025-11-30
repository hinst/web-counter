package org.hinst.web.counter;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/** https://stackoverflow.com/questions/67093198/how-can-i-set-up-my-application-to-only-return-messages-for-responsestatusexcept */
@ControllerAdvice
public class RestControllerAdvice {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleStatusException(ResponseStatusException exception) {
		throw exception;
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception exception) {
		return new ResponseEntity<>("Exception caught at " + ZonedDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
