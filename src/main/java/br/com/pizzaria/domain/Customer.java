package br.com.pizzaria.domain;

import java.util.List;

public class Customer {
	
	private SystemUser systemUser;
	private List<Pizza> cart;
	private CreditCard creditCard;
	
	public SystemUser getSystemUser() {
		return systemUser;
	}
	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
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
}
