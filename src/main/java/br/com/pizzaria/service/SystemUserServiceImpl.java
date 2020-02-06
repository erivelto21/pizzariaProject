package br.com.pizzaria.service;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pizzaria.dao.SystemUserDao;
import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.SystemUser;
import br.com.pizzaria.exception.EmailExistException;
import br.com.pizzaria.exception.SystemUserInvalidException;
import br.com.pizzaria.util.jwt.SystemUserValidation;

@Service
@Transactional
public class SystemUserServiceImpl implements SystemUserService{

	@Autowired
	private SystemUserDao dao;
	
	@Transactional(readOnly = true)
	public SystemUser getSystemUser(SystemUser user) {
		try {
			return dao.getSystemUser(user);
		} catch (NoResultException e) {
			throw new NoResultException("E-mail ou senha invalidos");
		}
	}
	
	@Transactional(readOnly = true)
	public SystemUser getSystemUser(String email) {
		try {
			return this.dao.getSystemUser(email);
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public SystemUser save(SystemUser user)  {
		if(this.getSystemUser(user.getEmail()) == null)
			if(SystemUserValidation.systemUserIsValid(user))
				return dao.save(user);
			else
				throw new SystemUserInvalidException("Dados inválidos");
		else
			throw new EmailExistException("Email já em uso");
	}
	
	public SystemUser updateAddress(SystemUser user) {
		if(user.getAddress().getId() == 0) {
			user.getAddress().setId(this.dao.saveAddress(user.getAddress()));
			this.dao.updateSystemUser(user);
		} else {
			if(!phoneIsEqual(user))
				this.dao.updateSystemUser(user);
			if(!addressIsEqual(user.getAddress()))
				this.dao.updateAddress(user.getAddress());
		}
		
		return user;
	}
	
	private boolean phoneIsEqual(SystemUser user) {
		return user.getPhone().equals(this.dao.getPhone(user.getId()));
	}
	
	private boolean addressIsEqual(Address address) {
		return address.toString().equals(this.dao.get(address.getId()).toString());
	}
}
