package br.com.pizzaria.service;

import java.math.BigDecimal;

import br.com.pizzaria.domain.DiscountCoupon;

public interface DiscountCouponService {

	DiscountCoupon get(String code);
	
	BigDecimal calculateDiscountAmount(BigDecimal amount, int discountValue);
	
	void increasedAmountOfUse(String code);
}
