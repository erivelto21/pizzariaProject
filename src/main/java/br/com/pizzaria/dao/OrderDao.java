package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.Order;

public interface OrderDao {

	public void save(Order order);
	
	public Order get(long id);
	
	public List<Order> getByUser(long id);
}