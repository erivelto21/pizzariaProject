package br.com.pizzaria.service;

import java.util.List;

import javax.persistence.NoResultException;

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
	
	public void create(Order order) {
		this.dao.persist(order);
	}

	@Transactional(readOnly = true)
	public Order get(long id) {
		List<Order> list = this.dao.find(id);

		if (list.size() > 0)
			return list.get(0);

		throw new NoResultException("Pedido não encontrado");
	}

	@Transactional(readOnly = true)
	public List<Order> getAllBySystemUserId(long id) {
		List<Order> list = this.dao.findBySystemUserId(id);
		
		if (list.size() > 0)
			return list;

		throw new NoResultException("Usuário não possui pedidos");
	}
}
