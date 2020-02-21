package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.Order;

public interface OrderDao {

	void save(Order order);
	
	List<Order> get(long id);
	
	List<Order> getByUser(long id);
}