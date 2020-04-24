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
	public SystemUser getSystemUser(long id) {
		List<SystemUser> list = this.dao.getSystemUser(id);
		
		if(list.size() > 0)
			return list.get(0);
		
		throw new NoResultException("Usuário não encontrado");
	}
	
	@Transactional(readOnly = true)
	public SystemUser getSystemUser(SystemUser user) {
		List<SystemUser> list = dao.getSystemUser(user);

		if (list.size() > 0)
			return list.get(0);

		throw new NoResultException("E-mail ou senha invalidos");
	}

	@Transactional(readOnly = true)
	public SystemUser getSystemUser(String email) {
		List<SystemUser> list = this.dao.getSystemUser(email);

		return list.size() > 0 ? list.get(0) : null;
	}

	public SystemUser save(SystemUser user) {
		this.emailIsNotUsed(user.getEmail());
		this.systemUserIsValid(user);

		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		SystemUser systemUser = dao.save(user);
		
		accountService.createAccount(systemUser);
		
		return systemUser;
	}

	public void addressManagement(Address address, long systemUserId) {
		SystemUserValidation.addressIsValid(address);

		SystemUser systemUser = this.getSystemUser(systemUserId);

		if (systemUser.getAddress() == null) {
			systemUser.setAddress(this.dao.saveAddress(address));
			this.dao.updateSystemUser(systemUser);
		} else {
			this.updateAddress(systemUser, address);
		}
	}
	
	public void phoneManagement(String phone, long systemUserId) {
		SystemUser systemUser = this.getSystemUser(systemUserId);

		SystemUserValidation.phoneIsValid(phone);
		
		if(systemUser.getPhone() == null) {
			systemUser.setPhone(phone);
			this.dao.updateSystemUser(systemUser);
		} else {
			this.updatePhone(systemUser, phone);
		}
	}

	private void updatePhone(SystemUser systemUser, String phone) {
		if (!systemUser.getPhone().equals(phone))
			this.dao.updateSystemUser(systemUser);
	}

	private void updateAddress(SystemUser systemUser, Address address) {
		if (!systemUser.getAddress().equals(address))
			this.dao.updateAddress(address);
	}

	private void emailIsNotUsed(String email) {
		if (getSystemUser(email) == null)
			return;
		throw new EmailExistException("Email já em uso");
	}

	private boolean systemUserIsValid(SystemUser user) {
		if (SystemUserValidation.systemUserIsValid(user))
			return true;
		throw new SystemUserInvalidException("Dados inválidos");
	}
}
