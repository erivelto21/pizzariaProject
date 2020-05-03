package br.com.pizzaria.service;

import java.util.List;

import br.com.pizzaria.domain.Order;

public interface OrderService {

	void create(Order order);
	
	Order get(long id);
	
	List<Order> getAllBySystemUserId(long id);
}
