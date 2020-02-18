package br.com.pizzaria.resource.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pizzaria.domain.Order;
import br.com.pizzaria.service.OrderService;

@RestController
@RequestMapping(value="/order", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
public class OrderRestController {

	@Autowired
	private OrderService service;
	
	@GetMapping(value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Order get(@PathVariable("id") long id) {
		return this.service.get(id);
	}
	
	@GetMapping(value="/user/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Order> getAllByUser(@PathVariable("id") long id) {
		return this.service.getAllByUser(id);
	}
}
