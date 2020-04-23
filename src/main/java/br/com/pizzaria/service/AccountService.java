package br.com.pizzaria.service;

import java.util.List;

import br.com.pizzaria.domain.Account;
import br.com.pizzaria.domain.Flavor;
import br.com.pizzaria.domain.SystemUser;

public interface AccountService {

	Account getByUser(long userId);
	
	List<Flavor> getFavoritesById(long accountId);
	
	Account addToFavorites(long accountId, long flavorId);
	
	Account removeFavorite(long accountId, long flavorId);
	
	void createAccount(SystemUser systemUser);
}
