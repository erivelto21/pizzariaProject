package br.com.pizzaria.resource.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pizzaria.domain.Customer;
import br.com.pizzaria.domain.CustomizedTransactionResponse;
import br.com.pizzaria.service.PaymentService;

@RestController
@RequestMapping(value="/payment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
public class PaymentRestController {
	
	@Autowired
	private PaymentService service;
	
	@PostMapping(value="/creditcard")
	@ResponseStatus(HttpStatus.CREATED)
	public CustomizedTransactionResponse creditCardPayment(@RequestBody Customer customer) {
		return this.service.creditCardPayment(customer);
	}
	
	@PostMapping(value="/creditcard/{discountCode}")
	@ResponseStatus(HttpStatus.CREATED)
	public CustomizedTransactionResponse creditCardPayment(@RequestBody Customer customer, @PathVariable("discountCode") String discountCode) {
		return this.service.creditCardPaymentWithDiscount(customer, discountCode);
	}
}
