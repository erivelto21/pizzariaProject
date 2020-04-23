package br.com.pizzaria.resource.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pizzaria.domain.Account;
import br.com.pizzaria.domain.Flavor;
import br.com.pizzaria.service.AccountService;

@RestController
@RequestMapping(value="/account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AccountRestController {

	@Autowired
	private AccountService service;
	
	@GetMapping("/favorite/{accountId}")
	@ResponseStatus(HttpStatus.OK)
	public List<Flavor> getFavoriteList(@PathVariable("accountId") long accountId) {
		return service.getFavoritesById(accountId);
	}
	
	@PatchMapping("/favorite/add/{accountId}/flavor/{flavorId}")
	@ResponseStatus(HttpStatus.OK)
	public Account addFavorite(@PathVariable("accountId") long accountId, @PathVariable("flavorId") long flavorId) {
		return this.service.addToFavorites(accountId, flavorId);
	}
	
	@PatchMapping("/favorite/remove/{accountId}/flavor/{flavorId}")
	@ResponseStatus(HttpStatus.OK)
	public Account removeFavorite(@PathVariable("accountId") long accountId, @PathVariable("flavorId") long flavorId) {
		return this.service.removeFavorite(accountId, flavorId);
	}
}
