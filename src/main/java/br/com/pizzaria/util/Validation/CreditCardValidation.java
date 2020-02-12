package br.com.pizzaria.util.Validation;

import br.com.pizzaria.domain.CreditCard;
import br.com.pizzaria.exception.CreditCardInvalidException;

public class CreditCardValidation {

	public static boolean creditCardIsValid(CreditCard creditCard) {
		if(!cardNumberIsValid(creditCard.getCardNumber() + "")) {
			throw new CreditCardInvalidException("Numero do cartão inválido");
		}
		
		if(!cardCvvIsValid(creditCard.getCardCvv() + "")) {
			throw new CreditCardInvalidException("Código CVV inválido");
		}
		
		if(!cardExpirationDateIsValid(creditCard.getCardExpirationDate() + "")) {
			throw new CreditCardInvalidException("Data de expiração inválida");
		}
		
		if(!cardHolderNameIsValid(creditCard.getCardHolderName())) {
			throw new CreditCardInvalidException("Nome do titular do cartão inválido");
		}
		
		return true;
	}
	
	private static boolean cardNumberIsValid(String cardNumber) {
		return cardNumber.length() == 16 && isOnlyNumbers(cardNumber);
	}
	
	private static boolean cardCvvIsValid(String cardCvv) {
		return cardCvv.length() == 3 && isOnlyNumbers(cardCvv);
	}
	
	private static boolean cardExpirationDateIsValid(String cardExpirationDate) {
		return cardExpirationDate.length() == 4 && isOnlyNumbers(cardExpirationDate);
	}
	
	private static boolean cardHolderNameIsValid(String cardHolderName) {
		return cardHolderName.length() > 3;
	}
	
	private static boolean isOnlyNumbers(String string) {
		String regex = "[0-9]+";
		return string.matches(regex);
	}
}
