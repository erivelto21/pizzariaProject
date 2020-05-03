package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.Order;

public interface OrderDao {

	void persist(Order order);
	
	List<Order> find(long id);
	
	List<Order> findBySystemUserId(long id);
}