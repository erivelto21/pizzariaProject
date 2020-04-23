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
	public Account getByUser(long userId) {
		List<Account> list = dao.getByUser(userId);
		
		if(list.size() > 0)
			return list.get(0);
		
		throw new NoResultException("Conta não encontrado pelo identificador do usuário");
	}
	
	@Transactional(readOnly = true)
	public List<Flavor> getFavoritesById(long accountId) {
		return this.getById(accountId).getFavorites();
	}
	
	public Account addToFavorites(long accountId, long flavorId) {
		Account account = this.getById(accountId);
		
		if(verifyFlavorOccurrence(account.getFavorites(), flavorId)) {
			throw new BadRequestException("Sabor já está na lista de favoritos");
		}
		
		Flavor flavor = flavorService.getById(flavorId);
		
		account.getFavorites().add(flavor);
		
		this.dao.updateAccount(account);
		
		return account;
	}
	
	public Account removeFavorite(long accountId, long flavorId) {
		Account account = this.getById(accountId);
		
		if(!verifyFlavorOccurrence(account.getFavorites(), flavorId)) {
			throw new BadRequestException("Sabor não está na lista de favoritos");
		}
		
		Flavor flavor = flavorService.getById(flavorId);
		
		account.getFavorites().remove(flavor);
		
		this.dao.updateAccount(account);
		
		return account;
	}
	
	public void createAccount(SystemUser systemUser) {
		Account account = new Account();
		account.setSystemUser(systemUser);
		
		this.dao.save(account);
	}
	
	@Transactional(readOnly = true)
	private Account getById(long accountId) {
		List<Account> list = dao.getById(accountId);
		
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
