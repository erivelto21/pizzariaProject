package br.com.pizzaria.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pizzaria.dao.AccountDao;
import br.com.pizzaria.domain.Account;
import br.com.pizzaria.domain.Flavor;
import br.com.pizzaria.domain.SystemUser;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountDao dao;
	
	@Autowired
	private FlavorService flavorService;
	
	@Transactional(readOnly = true)
	public Account getBySystemUser(long userId) {
		List<Account> list = dao.findBySystemUserId(userId);
		
		if(list.size() > 0)
			return list.get(0);
		
		throw new NoResultException("Conta não encontrado pelo identificador do usuário");
	}
	
	@Transactional(readOnly = true)
	public Account getBySystemUserEmail(String email) {
		List<Account> list = dao.findBySystemUserEmail(email);
		
		if(list.size() > 0)
			return list.get(0);
		
		throw new NoResultException("Conta não encontrado pelo email do usuário");
	}
	
	@Transactional(readOnly = true)
	public List<Flavor> getFavoritesByAccountId(long accountId) {
		return this.get(accountId).getFavorites();
	}
	
	public Account addToFavorites(long accountId, long flavorId) {
		Account account = this.get(accountId);
		
		if(verifyFlavorOccurrence(account.getFavorites(), flavorId)) {
			throw new BadRequestException("Sabor já está na lista de favoritos");
		}
		
		Flavor flavor = flavorService.get(flavorId);
		
		account.getFavorites().add(flavor);
		
		this.dao.merge(account);
		
		return account;
	}
	
	public Account removeFavorite(long accountId, long flavorId) {
		Account account = this.get(accountId);
		
		if(!verifyFlavorOccurrence(account.getFavorites(), flavorId)) {
			throw new BadRequestException("Sabor não está na lista de favoritos");
		}
		
		Flavor flavor = flavorService.get(flavorId);
		
		account.getFavorites().remove(flavor);
		
		this.dao.merge(account);
		
		return account;
	}
	
	public void create(SystemUser systemUser) {
		Account account = new Account();
		account.setSystemUser(systemUser);
		
		this.dao.persist(account);
	}
	
	@Transactional(readOnly = true)
	private Account get(long accountId) {
		List<Account> list = dao.find(accountId);
		
		if(list.size() > 0)
			return list.get(0);
		
		throw new NoResultException("Conta não encontrado");
	}
	
	private boolean verifyFlavorOccurrence(List<Flavor> favorites, long flavorId) {
		for(Flavor flavor: favorites) {
			if(flavor.getId() == flavorId)
				return true;
		}
		
		return false;
	}
}
