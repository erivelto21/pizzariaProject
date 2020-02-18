package br.com.pizzaria.resource.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pizzaria.domain.SystemUser;
import br.com.pizzaria.service.SystemUserService;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

	@Autowired
	private SystemUserService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SystemUser save(@RequestBody SystemUser user) {
		return this.service.save(user);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public SystemUser updateAddress(@RequestBody SystemUser user) {
		return this.service.updateAddress(user);
	}
}
