package br.com.pizzaria.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

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
import br.com.pizzaria.domain.DiscountCoupon;
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
	private OrderService orderService;
	
	@Autowired
	private DiscountCouponService discountCouponService;
	
	private String apiKey = "ak_test_H8wlboVwKyJ1rOyAC24mI633C0xvCu";
	private static String END_POINT = "https://api.pagar.me/1/";
	private Client client;
	private WebTarget webTarget;
	
	public PaymentServiceImpl() {
		this.client = ClientBuilder.newClient();
	}
	
	public CustomizedTransactionResponse creditCardPayment(Customer customer) {
		CustomerValidation.customerIsValid(customer);
		
		customer.setAmount(this.getTotalAmount(customer.getCart()));
		
		return this.makeTransaction(customer);
	}
	
	public CustomizedTransactionResponse creditCardPaymentWithDiscount(Customer customer, String discountCode) {
		CustomerValidation.customerIsValid(customer);
		
		customer.setAmount(this.applyDiscount(this.getTotalAmount(customer.getCart()), discountCode));
		
		return this.makeTransaction(customer);
	}
	
	private CustomizedTransactionResponse makeTransaction(Customer customer) {
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
		order.setDeliveryFee(new BigDecimal("10.00"));
		order.setUser(customer.getSystemUser());
		order.setTotal(customer.getAmount().add(order.getDeliveryFee()));
		order.setPaymentWay(customer.getPaymentWay());
		order.setDate(Calendar.getInstance());

		order.setTransactionId(JsonUtil.getJsonValue(transactionResponse, "tid"));
		order.setTransactionStatus(JsonUtil.getJsonValue(transactionResponse, "status"));
		
		this.orderService.create(order);
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
	
	private BigDecimal getTotalAmount(List<Pizza> cart) {
		if(cart == null)
			throw new CustomerInvalidException("Carrinho vazio");

		BigDecimal amount = new BigDecimal("00.00");

		for(Pizza orderedPizza: cart) {
			orderedPizza.calculateAdditionals();
			int aux = orderedPizza.getAmount();
			amount = amount
					.add(orderedPizza.calculatePrizePerSize().add(orderedPizza.getAdditionalsValue())
					.multiply(new BigDecimal(aux)));
		}

		return amount;
	}
	
	private BigDecimal applyDiscount(BigDecimal amount, String discountCode) {
		DiscountCoupon discountCoupon = this.discountCouponService.get(discountCode);
		
		this.discountCouponService.increasedAmountOfUse(discountCode);
		
		return this.discountCouponService.calculateDiscountAmount(amount, discountCoupon.getPercentageDiscount());
	}
}
