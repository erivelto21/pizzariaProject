package br.com.pizzaria.resource.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pizzaria.domain.DiscountCoupon;
import br.com.pizzaria.service.DiscountCouponService;

@RestController
@RequestMapping(value="/discountcoupon", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
public class DiscountCouponRestController {

	@Autowired
	private DiscountCouponService service;
	
	@GetMapping("/{discountCode}")
	@ResponseStatus(HttpStatus.OK)
	public DiscountCoupon get(@PathVariable("discountCode") String discountCode) {
		return this.service.get(discountCode);
	}
}
