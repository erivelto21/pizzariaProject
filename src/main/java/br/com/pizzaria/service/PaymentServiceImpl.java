package br.com.pizzaria.service;

import java.io.IOException;
import java.util.Calendar;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.pizzaria.domain.Customer;
import br.com.pizzaria.domain.CustomizedTransactionResponse;
import br.com.pizzaria.domain.Order;
import br.com.pizzaria.domain.Pizza;
import br.com.pizzaria.util.Validation.CreditCardValidation;
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
		
		if(!CreditCardValidation.creditCardIsValid(customer.getCreditCard()))
			return null;
		
		this.webTarget = this.client.target(END_POINT).path("transactions");

		String transactionResponse = this.webTarget.request("application/json;charset=UTF-8")
				.post(Entity.entity(this.customerToTransactionJson(customer), MediaType.APPLICATION_JSON))
				.readEntity(String.class);
		
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
		
		order.setTransactionId(this.getJsonValue(transactionResponse, "tid"));
		order.setTransactionStatus(this.getJsonValue(transactionResponse, "status"));
		
		this.service.createOrder(order);
	}
	
	private CustomizedTransactionResponse getCustomizedTransactionResponse(String transactionResponse) {
		String status = this.getJsonValue(transactionResponse, "status");

		if(status.equals("refused"))
			return new CustomizedTransactionResponse("Transação recusada", status);
		if(status.equals("paid"))
			return new CustomizedTransactionResponse("Transação aprovada", status);
		
		return new CustomizedTransactionResponse("Transação resultou em: " + status, status);
	}
	
	private String customerToTransactionJson(Customer customer) {
		String transactionJson = "";
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		TransactionsPagarMeBuilder transactionsPagarMeBuilder = new TransactionsPagarMeBuilder(customer, apiKey);
		
		try {
			transactionJson = objectMapper.writeValueAsString(transactionsPagarMeBuilder.Builder());
		} catch (JsonProcessingException e) {
			throw new InternalServerErrorException("Um error aconteceu, error: error durante a conversão da Jtransação");
		}
		
		return transactionJson;
	}
	
	private String getJsonValue(String json, String name) {
		String value = "";
		
		try {
			ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);

			if (node.has(name))
				value = removeDoubleQuotes(node.get(name).toString());
		} catch (IOException e) {
			throw new InternalServerErrorException("Um error aconteceu, error: error durante a obtenção do valor do json");
		}
		
		return value;
	}
	
	private String removeDoubleQuotes(String string) {
		return string.replace("\"", "");
	}

}
