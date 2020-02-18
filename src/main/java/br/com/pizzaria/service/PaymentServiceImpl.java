package br.com.pizzaria.service;

import java.util.Calendar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pizzaria.domain.Customer;
import br.com.pizzaria.domain.CustomizedTransactionResponse;
import br.com.pizzaria.domain.Order;
import br.com.pizzaria.domain.Pizza;
import br.com.pizzaria.exception.CustomerInvalidException;
import br.com.pizzaria.util.JsonUtil;
import br.com.pizzaria.util.Validation.CustomerValidation;
import br.com.pizzaria.util.builder.TransactionsPagarMeBuilder;


@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private OrderService service;
	
	private String apiKey = "ak_test_H8wlboVwKyJ1rOyAC24mI633C0xvCu";
	private static String END_POINT = "https://api.pagar.me/1/";
	private Client client;
	private WebTarget webTarget;
	
	public PaymentServiceImpl() {
		this.client = ClientBuilder.newClient();
	}
	
	public CustomizedTransactionResponse creditCardPayment(Customer customer) {
		
		CustomerValidation.customerIsValid(customer);
		
		this.webTarget = this.client.target(END_POINT).path("transactions");

		String transactionResponse = this.webTarget.request("application/json;charset=UTF-8")
				.post(Entity.entity(this.customerToTransactionJson(customer), MediaType.APPLICATION_JSON))
				.readEntity(String.class);
		System.out.println(transactionResponse);
		this.saveTransaction(customer, transactionResponse);
		
		return this.getCustomizedTransactionResponse(transactionResponse);
	}
	
	private void saveTransaction(Customer customer, String transactionResponse) {
		Order order = new Order();
		
		for(Pizza p: customer.getCart())
			p.setOrder(order);
		
		order.setPizzas(customer.getCart());
		order.setUser(customer.getSystemUser());
		order.setTotal(customer.getAmount());
		order.setPaymentWay(customer.getPaymentWay());
		order.setDate(Calendar.getInstance());
		
		order.setTransactionId(JsonUtil.getJsonValue(transactionResponse, "tid"));
		order.setTransactionStatus(JsonUtil.getJsonValue(transactionResponse, "status"));
		
		this.service.createOrder(order);
	}
	
	private CustomizedTransactionResponse getCustomizedTransactionResponse(String transactionResponse) {
		String status = JsonUtil.getJsonValue(transactionResponse, "status");
		String type = JsonUtil.getJsonValue(transactionResponse, "errors");

		if(!type.equals(""))
			throw new CustomerInvalidException("Ocorreu um error com a plataforma de pagamentos online");
		if(status.equals("refused"))
			return new CustomizedTransactionResponse("Transação recusada", status);
		if(status.equals("paid"))
			return new CustomizedTransactionResponse("Transação aprovada", status);
		
		return new CustomizedTransactionResponse("Transação resultou em: " + status, status);
	}
	
	private String customerToTransactionJson(Customer customer) {
		
		TransactionsPagarMeBuilder transactionsPagarMeBuilder = new TransactionsPagarMeBuilder(customer, apiKey);
		
		
		String transactionJson = JsonUtil.objectToJson(transactionsPagarMeBuilder.Builder());
		
		
		return transactionJson;
	}
}
