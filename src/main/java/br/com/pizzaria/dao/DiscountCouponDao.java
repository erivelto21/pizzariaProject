package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.DiscountCoupon;

public interface DiscountCouponDao {

	List<DiscountCoupon> find(String code);
	
	void merge(DiscountCoupon discountCoupon);
}
