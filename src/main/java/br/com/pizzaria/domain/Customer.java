package br.com.pizzaria.domain;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
	
	@JsonProperty(value = "user")
	private SystemUser systemUser;
	
	private String paymentWay;
	
	private List<Pizza> cart;
	
	private CreditCard creditCard;
	
	public SystemUser getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

	public String getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(String paymentWay) {
		this.paymentWay = paymentWay;
	}

	public List<Pizza> getCart() {
		return cart;
	}

	public void setCart(List<Pizza> cart) {
		this.cart = cart;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public BigDecimal getAmount() {
		BigDecimal amount = new BigDecimal("00.00");

		for(Pizza orderedPizza: this.cart) {
			int aux = orderedPizza.getAmount();
			amount = amount.add(orderedPizza.getFlavor().getPrice().multiply(new BigDecimal(aux)));
		}

		return amount;
	}
}
