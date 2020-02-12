package br.com.pizzaria.exception;

public class CreditCardInvalidException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CreditCardInvalidException(String message) {
		super(message);
	}

}
