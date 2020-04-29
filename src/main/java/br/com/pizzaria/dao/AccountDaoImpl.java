package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pizzaria.domain.Account;

@Repository
public class AccountDaoImpl implements AccountDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Account> getByUser(long userId) {
		return this.entityManager
				.createQuery("select a from Account a where a.systemUser.id = :userId", Account.class)
				.setParameter("userId", userId)
				.getResultList();
	}
	
	public List<Account> getByUserEmail(String email) {
		return this.entityManager
				.createQuery("select a from Account a where a.systemUser.email = :email", Account.class)
				.setParameter("email", email)
				.getResultList();
	}
	
	public List<Account> getById(long accountId) {
		return this.entityManager
				.createQuery("select a from Account a where a.id = :accountId", Account.class)
				.setParameter("accountId", accountId)
				.getResultList();
	}

	public void save(Account account) { 
		this.entityManager.persist(account);
	}
	
	public void updateAccount(Account account) {
		this.entityManager.merge(account);	
	}
}
