package es.mdef.apigatel.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//import es.mdef.gestionusuarios.REST.RegisterNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;

@RestControllerAdvice
public class ControladorExcepciones {

	@ResponseBody
	@ExceptionHandler(RegisterNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String usuarioNotFoundHandler(RegisterNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(ArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String argumentoNoValido(ArgumentNotValidException ex) {
		return ex.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleValidationExceptions2(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach((violation) -> {
			String fieldName = violation.getPropertyPath().toString();
			String errorMessage = violation.getMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return error;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UnexpectedTypeException.class)
	public Map<String, String> handleUnexpectedTypeException(UnexpectedTypeException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("Error: ", ex.getMessage());
		return error;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("Error", ex.getMessage());
		return error;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public Map<String, String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getRootCause().getMessage());
		return error;
	}

}