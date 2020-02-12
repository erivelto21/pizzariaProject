package br.com.pizzaria.service;

import java.util.List;

import br.com.pizzaria.domain.Order;

public interface OrderService {

	public void createOrder(Order order);
	
	public Order get(long id);
	
	public List<Order> getAllByUser(long id);
}
