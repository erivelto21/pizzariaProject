package br.com.pizzaria.resource.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pizzaria.domain.Flavor;
import br.com.pizzaria.service.FlavorService;

@RestController
@RequestMapping(value="/flavor", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FlavorRestController {
	
	@Autowired
	private FlavorService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Flavor> getAllFlavors(){
		return this.service.getAllFlavorsList();
	}

}
