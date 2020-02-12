package br.com.pizzaria.resource.rest.exception;

import javax.persistence.NoResultException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.pizzaria.exception.CreditCardInvalidException;
import br.com.pizzaria.exception.EmailExistException;
import br.com.pizzaria.exception.SystemUserInvalidException;

@ControllerAdvice
public class SystemUserExceptionController {

	@ExceptionHandler(value= {EmailExistException.class, SystemUserInvalidException.class, CreditCardInvalidException.class})
	public ResponseEntity<Object> badRequestExceptions(Exception exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value= {NoResultException.class})
	public ResponseEntity<Object> NotFoundExceptions(Exception exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}
