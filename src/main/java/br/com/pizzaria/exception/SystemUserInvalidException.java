package br.com.pizzaria.exception;

public class SystemUserInvalidException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public SystemUserInvalidException(String message) {
		super(message);
	}

}
