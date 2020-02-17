package br.com.pizzaria.resource.rest.exception;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.pizzaria.exception.CreditCardInvalidException;
import br.com.pizzaria.exception.CustomerInvalidException;
import br.com.pizzaria.exception.EmailExistException;
import br.com.pizzaria.exception.SystemUserInvalidException;
import br.com.pizzaria.service.ErrorMessageService;

@ControllerAdvice
public class SystemUserExceptionController {

	@Autowired
	private ErrorMessageService service;
	
	@ExceptionHandler(value=
		{EmailExistException.class, 
				CustomerInvalidException.class, SystemUserInvalidException.class, CreditCardInvalidException.class})
	public ResponseEntity<Object> badRequestExceptions(Exception exception){
		return new ResponseEntity<>(
				this.service.response(
						exception.getMessage(), 
						exception.getLocalizedMessage(), 
						HttpStatus.BAD_REQUEST), 
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value= {NoResultException.class})
	public ResponseEntity<Object> NotFoundExceptions(Exception exception){
		return new ResponseEntity<>(
				this.service.response(
						exception.getMessage(), 
						exception.getLocalizedMessage(),  
						HttpStatus.NOT_FOUND),  
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<Object> Exceptions(Exception exception){
		return new ResponseEntity<>(
				this.service.response(
						exception.getMessage(), 
						exception.getLocalizedMessage(), 
						HttpStatus.INTERNAL_SERVER_ERROR), 
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
