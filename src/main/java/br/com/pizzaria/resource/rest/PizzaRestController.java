package br.com.pizzaria.resource.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pizzaria.domain.RankItem;
import br.com.pizzaria.service.PizzaService;

@RestController
@RequestMapping(value="/pizza", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
public class PizzaRestController {

	@Autowired
	private PizzaService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<RankItem> getPizzaRank(){
		return service.getPizzaRank();
	}
}
