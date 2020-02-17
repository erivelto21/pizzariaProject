package br.com.pizzaria.exception;

public class CustomerInvalidException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CustomerInvalidException(String message) {
		super(message);
	}
}
