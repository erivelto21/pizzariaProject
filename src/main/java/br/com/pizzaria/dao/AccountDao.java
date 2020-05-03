package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.Account;

public interface AccountDao {

	List<Account> findBySystemUserId(long userId);
	
	List<Account> findBySystemUserEmail(String email);

	List<Account> find(long accountId);
	
	void persist(Account account);
	
	void merge(Account account);
}
