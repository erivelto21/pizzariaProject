package br.com.pizzaria.util.Validation;

import java.util.List;

import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.CreditCard;
import br.com.pizzaria.domain.Customer;
import br.com.pizzaria.domain.Pizza;
import br.com.pizzaria.exception.CustomerInvalidException;

public class CustomerValidation {

	public static boolean customerIsValid(Customer customer) {
		if(!addressIsValid(customer.getSystemUser().getAddress())) {
			throw new CustomerInvalidException("Endereço inválido");
		}
		
		if(!phoneIsValid(customer.getSystemUser().getPhone())) {
			throw new CustomerInvalidException("Telefone inválido");
		}
		
		if(!paymentWayIsValid(customer.getPaymentWay())) {
			throw new CustomerInvalidException("Forma de pagamento inválido");
		}
		
		if(!cartIsValid(customer.getCart())) {
			throw new CustomerInvalidException("Carrinho vazio");
		}
		
		if(customer.getPaymentWay().equals("Cartão de crédito")) {
			if(!creditCardIsValid(customer.getCreditCard())) {
				throw new CustomerInvalidException("Cartão invalido");
			}
		}
		
		return true;
	}
	
	private static boolean addressIsValid(Address address) {
		return address != null;
	}
	
	private static boolean phoneIsValid(String phone) {
		return phone.length() > 14;
	}
	
	private static boolean paymentWayIsValid(String paymentWay) {
		return paymentWay.length() > 5;
	}
	
	private static boolean cartIsValid(List<Pizza> cart) {
		return cart.size() > 0;
	}
	
	private static boolean creditCardIsValid(CreditCard creditCard) {
		return CreditCardValidation.creditCardIsValid(creditCard);
	}
}
