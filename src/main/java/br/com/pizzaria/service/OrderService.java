package br.com.pizzaria.service;

import java.util.List;

import br.com.pizzaria.domain.Order;

public interface OrderService {

	void createOrder(Order order);
	
	Order get(long id);
	
	List<Order> getAllByUser(long id);
}
