package br.com.pizzaria.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pizzaria.dao.DiscountCouponDao;
import br.com.pizzaria.domain.DiscountCoupon;
import br.com.pizzaria.exception.DiscountCouponExpired;

@Service
@Transactional
public class DiscountCouponServiceImpl implements DiscountCouponService{

	@Autowired
	private DiscountCouponDao dao;
	
	@Transactional(readOnly = true)
	public DiscountCoupon get(String code) {
		List<DiscountCoupon> list = dao.find(code);
		
		if(list.size() == 0)
			throw new NoResultException("Cupom não encontrado");
		
		DiscountCoupon discountCoupon = list.get(0);
		
		if(this.discountCouponIsNotExpired(discountCoupon))
			return discountCoupon;
		
		throw new DiscountCouponExpired("Cupom expirado");
	}
	
	public void increasedAmountOfUse(String code) {
		DiscountCoupon discountCoupon = this.get(code);
		discountCoupon.setAmountUsed(discountCoupon.getAmountUsed()+1);
		
		dao.merge(discountCoupon);
	}
	
	public BigDecimal calculateDiscountAmount(BigDecimal amount, int discountValue) {
		if(discountValue == 0) {
			return amount;
		}
		
		return amount.subtract(this.calculateDiscountValue(amount, discountValue));
	}
	
	private boolean discountCouponIsNotExpired(DiscountCoupon discountCoupon) {
		return Calendar.getInstance().before(discountCoupon.getExpirationDate());
	}
	
	private BigDecimal calculateDiscountValue(BigDecimal amount, int discountValue) {
		return amount.multiply(new BigDecimal("0." + discountValue));
	}
}
