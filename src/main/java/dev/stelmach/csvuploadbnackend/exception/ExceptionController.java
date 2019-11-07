package dev.stelmach.csvuploadbnackend.exception;

import dev.stelmach.csvuploadbnackend.rest.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

@EnableWebMvc
@ControllerAdvice
public class ExceptionController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String INVALID_ENTRY = "Invalid entry exception.";

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiResponse> handle404Exception(NoHandlerFoundException e) {
		ApiResponse error = new ApiResponse();
		error.setStatus(404);
		error.setMessage("End-point not found.");
		error.setResult(e.getMessage());
		log(error);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ApiResponse> handleConstraintViolationExceptionExceptionException(IOException e) {
		ApiResponse error = new ApiResponse();
		error.setStatus(400);
		error.setMessage("IO Exception");
		error.setResult(e.getMessage());
		log(error);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public ResponseEntity<ApiResponse> handleArrayIndexOutOfBoundsExceptionException(ArrayIndexOutOfBoundsException e) {
		ApiResponse error = new ApiResponse();
		error.setStatus(400);
		error.setMessage(INVALID_ENTRY);
		error.setResult(e.getMessage());
		log(error);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidEntryException.class)
	public ResponseEntity<ApiResponse> handleDateFormatException(InvalidEntryException e) {
		ApiResponse error = new ApiResponse();
		error.setStatus(400);
		error.setMessage(e.getMessage());
		error.setResult(e.getMessage());
		log(error);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> handleIllegalArgumentExceptionException(IllegalArgumentException e) {
		ApiResponse error = new ApiResponse();
		error.setStatus(400);
		error.setMessage(INVALID_ENTRY);
		error.setResult(e.getMessage());
		log(error);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	private void log(ApiResponse error) {
		if (log.isErrorEnabled()) {
			String errorMsg = "Error code: %s - %s";
			log.error(String.format(errorMsg, error.getStatus(), error.getResult()));
		}
	}
}
