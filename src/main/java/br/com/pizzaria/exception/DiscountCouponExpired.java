package br.com.pizzaria.exception;

public class DiscountCouponExpired extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DiscountCouponExpired(String message) {
		super(message);
	}
}
