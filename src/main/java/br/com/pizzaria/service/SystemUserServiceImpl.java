package br.com.pizzaria.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pizzaria.dao.SystemUserDao;
import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.SystemUser;
import br.com.pizzaria.exception.EmailExistException;
import br.com.pizzaria.exception.EqualPasswordException;
import br.com.pizzaria.exception.SystemUserInvalidException;
import br.com.pizzaria.util.Validation.SystemUserValidation;

@Service
@Transactional
public class SystemUserServiceImpl implements SystemUserService {

	@Autowired
	private SystemUserDao dao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public SystemUser get(long id) {
		List<SystemUser> list = this.dao.find(id);
		
		if(list.size() > 0)
			return list.get(0);
		
		throw new NoResultException("Usuário não encontrado");
	}
	
	@Transactional(readOnly = true)
	public SystemUser get(SystemUser user) {
		List<SystemUser> list = dao.find(user);

		if (list.size() > 0)
			return list.get(0);

		throw new NoResultException("E-mail ou senha invalidos");
	}

	@Transactional(readOnly = true)
	public SystemUser get(String email) {
		List<SystemUser> list = this.dao.find(email);

		return list.size() > 0 ? list.get(0) : null;
	}

	public SystemUser save(SystemUser user) {
		this.emailIsNotUsed(user.getEmail());
		this.systemUserIsValid(user);

		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		SystemUser systemUser = dao.persist(user);
		
		accountService.create(systemUser);
		
		return systemUser;
	}
	
	public void passwordManagement(String password, long systemUserId) {
		if(!SystemUserValidation.passwordIsValid(password))
			throw new SystemUserInvalidException("Senha inválida");
		
		SystemUser systemUser = this.get(systemUserId);
		
		if(this.passwordIsEqual(password, systemUser.getPassword()))
			throw new EqualPasswordException("Nova senha é igual a antiga");
		
		password = this.passwordEncoder.encode(password);
		
		systemUser.setPassword(password);
		
		this.updatePassword(systemUser);
	}

	public void addressManagement(Address address, long systemUserId) {
		SystemUserValidation.addressIsValid(address);

		SystemUser systemUser = this.get(systemUserId);

		if (systemUser.getAddress() == null) {
			systemUser.setAddress(this.dao.persistAddress(address));
			this.dao.update(systemUser);
		} else {
			this.updateAddress(systemUser, address);
		}
	}
	
	public void phoneManagement(String phone, long systemUserId) {
		SystemUser systemUser = this.get(systemUserId);

		SystemUserValidation.phoneIsValid(phone);
		
		if(systemUser.getPhone() == null) {
			systemUser.setPhone(phone);
			this.dao.update(systemUser);
		} else {
			this.updatePhone(systemUser, phone);
		}
	}
	
	private boolean passwordIsEqual(String newPassword, String oldPassword) {
		return this.passwordEncoder.matches(newPassword, oldPassword);
	}

	private void updatePassword(SystemUser systemUser) {
		this.dao.update(systemUser);
	}
	
	private void updatePhone(SystemUser systemUser, String phone) {
		if (!systemUser.getPhone().equals(phone))
			this.dao.update(systemUser);
	}

	private void updateAddress(SystemUser systemUser, Address address) {
		if (!systemUser.getAddress().equals(address))
			this.dao.updateAddress(address);
	}

	private void emailIsNotUsed(String email) {
		if (get(email) == null)
			return;
		throw new EmailExistException("Email já em uso");
	}

	private boolean systemUserIsValid(SystemUser user) {
		if (SystemUserValidation.systemUserIsValid(user))
			return true;
		throw new SystemUserInvalidException("Dados inválidos");
	}
}
