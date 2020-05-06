package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pizzaria.domain.DiscountCoupon;

@Repository
public class DiscountCouponDaoImpl implements DiscountCouponDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<DiscountCoupon> find(String code) {
		return this.entityManager
				.createQuery("select d from DiscountCoupon d where d.code = :code", DiscountCoupon.class)
				.setParameter("code", code)
				.getResultList();
	}

	public void merge(DiscountCoupon discountCoupon) {
		this.entityManager.merge(discountCoupon);
	}
}
