package br.com.pizzaria.util.Validation;

import java.util.List;

import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.CreditCard;
import br.com.pizzaria.domain.CustomFlavor;
import br.com.pizzaria.domain.Customer;
import br.com.pizzaria.domain.Pizza;
import br.com.pizzaria.exception.CustomerInvalidException;

public class CustomerValidation {

	public static boolean customerIsValid(Customer customer) {
		customerIsNull(customer);
		if (!addressIsValid(customer.getSystemUser().getAddress())) {
			throw new CustomerInvalidException("Endereço inválido");
		}

		if (!phoneIsValid(customer.getSystemUser().getPhone())) {
			throw new CustomerInvalidException("Telefone inválido");
		}

		if (!paymentWayIsValid(customer.getPaymentWay())) {
			throw new CustomerInvalidException("Forma de pagamento inválido");
		}

		if (!cartIsValid(customer.getCart())) {
			throw new CustomerInvalidException("Carrinho vazio");
		}
		
		if(!(cartItemsAreValid(customer.getCart()))) {
			
		}

		if (customer.getPaymentWay().equals("Cartão de crédito")) {
			if (!creditCardIsValid(customer.getCreditCard())) {
				throw new CustomerInvalidException("Cartão invalido");
			}
		} else {
			throw new CustomerInvalidException("Forma de pagamento inválido");
		}

		return true;
	}

	private static void customerIsNull(Customer customer) {
		if (customer == null)
			throw new CustomerInvalidException("Cliente vazio");
		if (customer.getCart() == null)
			throw new CustomerInvalidException("Carrinho vazio");
		if (customer.getCreditCard() == null)
			throw new CustomerInvalidException("Cartão vazio");
		if (customer.getPaymentWay() == null)
			throw new CustomerInvalidException("Método de pagamento vazio");
		if (customer.getSystemUser() == null)
			throw new CustomerInvalidException("Usuário vazio");
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

	private static boolean cartItemsAreValid(List<Pizza> list) {
		for (Pizza p : list) {
			customFlavorIsValid(p.getCustomFlavor());
		}

		return true;
	}

	private static boolean customFlavorIsValid(CustomFlavor customFlavor) {
		if (customFlavor == null) {
			throw new CustomerInvalidException("Algum item do carrinho está vazio");
		}
		if (customFlavor.getIngredients() == null) {
			throw new CustomerInvalidException("Lista de ingredientes nula");
		}
		if (!(customFlavor.getIngredients().size() > 0)) {
			throw new CustomerInvalidException("Lista de ingredientes vazia");
		}
		if (customFlavor.getImage().equals("") || customFlavor.getImage().length() < 3) {
			throw new CustomerInvalidException("Imagem inválido");
		}
		if (customFlavor.getName().equals("") || customFlavor.getName().length() < 3) {
			throw new CustomerInvalidException("Nome inválido");
		}
		if (customFlavor.getPrice().doubleValue() == 0) {
			throw new CustomerInvalidException("Preço inválido");
		}
		if (customFlavor.getType() == null) {
			throw new CustomerInvalidException("Tipo vazio");
		}

		return true;
	}
}
