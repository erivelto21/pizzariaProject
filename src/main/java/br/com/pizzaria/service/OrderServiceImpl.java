package br.com.pizzaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pizzaria.dao.OrderDao;
import br.com.pizzaria.domain.Order;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderDao dao;
	
	public void createOrder(Order order) {
		this.dao.save(order);
	}

	@Transactional(readOnly = true)
	public Order get(long id) {
		return this.dao.get(id);
	}

	@Transactional(readOnly = true)
	public List<Order> getAllByUser(long id) {
		return this.dao.getByUser(id);
	}
}
