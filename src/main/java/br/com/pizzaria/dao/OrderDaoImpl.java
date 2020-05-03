package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pizzaria.domain.Order;

@Repository
public class OrderDaoImpl implements OrderDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void persist(Order order) {
		this.entityManager.persist(order);
	}

	public List<Order> find(long id) {
		return this.entityManager
				.createQuery("select o from Order o where o.id = :id", Order.class)
				.setParameter("id", id)
				.getResultList();
	}

	public List<Order> findBySystemUserId(long id) {
		return this.entityManager
				.createQuery("select o from Order o where o.user.id = :user", Order.class)
				.setParameter("user", id)
				.getResultList();
	}
}
