package com.bank.card.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.validation.UnexpectedTypeException;
import lombok.extern.java.Log;

@Log
@Component
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
		log.warning("Error controlado: " + ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(400, "DECLINADO", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.warning("Información invalida: " + ex.getFieldErrors());
		ErrorResponse errorResponse = new ErrorResponse(4001, "INFORMACIÓN INVALIDA",
				ex.getFieldErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnexpectedTypeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleUnexpectedTypeException(UnexpectedTypeException ex) {
		log.warning("Información incompleta: " + ex.getLocalizedMessage());
		ex.printStackTrace();
		ErrorResponse errorResponse = new ErrorResponse(4002, "INFORMACIÓN INCOMPLETA",
				ex.getLocalizedMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	

	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
		log.info("Recurso no encontrado...");
		ErrorResponse errorResponse = new ErrorResponse(404, "RECURSO NO ENCONTRADO",
				"Verifique información, PATH [" + ex.getResourcePath() + "]");
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		log.info("Metodo no permitido...");
		ErrorResponse errorResponse = new ErrorResponse(405, "METODO NO PERMITIDO",
				"Metodo no permitido,  [" + ex.getMessage() + "]");
		return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
		ex.printStackTrace();
		log.severe("Error no controlado: " + ex.getLocalizedMessage());
		ErrorResponse errorResponse = new ErrorResponse(999, "ERROR INTERNO",
				"Error interno procesando la petición... [" + ex.getMessage() + "]");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
