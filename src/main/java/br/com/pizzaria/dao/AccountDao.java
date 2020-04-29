package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.Account;

public interface AccountDao {

	List<Account> getByUser(long userId);
	
	List<Account> getByUserEmail(String email);

	List<Account> getById(long accountId);
	
	void save(Account account);
	
	void updateAccount(Account account);
}
