package br.com.pizzaria.exception;

public class EqualPasswordException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EqualPasswordException(String message) {
		super(message);
	}
}
