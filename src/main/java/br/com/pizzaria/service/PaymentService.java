package br.com.pizzaria.service;

import br.com.pizzaria.domain.Customer;
import br.com.pizzaria.domain.CustomizedTransactionResponse;

public interface PaymentService {
	
	CustomizedTransactionResponse creditCardPayment(Customer customer);
}
