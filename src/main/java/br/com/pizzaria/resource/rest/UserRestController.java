package br.com.pizzaria.resource.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pizzaria.domain.Address;
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
	
	@PatchMapping("/{systemUserId}/phone")
	@ResponseStatus(HttpStatus.OK)
	public void updatePhone(@PathVariable("systemUserId") long systemUserId, @RequestBody String phone) {
		this.service.phoneManagement(phone, systemUserId);
	}
	
	@PatchMapping("/{systemUserId}/address")
	@ResponseStatus(HttpStatus.OK)
	public void updateAddress(@PathVariable("systemUserId") long systemUserId, @RequestBody Address address) {
		this.service.addressManagement(address, systemUserId);
	}
}
