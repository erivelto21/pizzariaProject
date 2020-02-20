package br.com.pizzaria.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
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

		return list.size() > 0 ? list.get(0) : new SystemUser();
	}

	public SystemUser save(SystemUser user) {
		this.emailIsNotUsed(user.getEmail());
		this.SystemUserIsValid(user);

		return dao.save(user);
	}

	public SystemUser createAddress(SystemUser user) {
		if (user.getAddress().getId() == 0) {
			user.getAddress().setId(this.dao.saveAddress(user.getAddress()));
			this.dao.updateSystemUser(user);
		} else {
			this.updatePhone(user);
			this.updateAddress(user);
		}

		return user;
	}

	private void updatePhone(SystemUser user) {
		if (!phoneIsEqual(user))
			this.dao.updateSystemUser(user);
	}

	private void updateAddress(SystemUser user) {
		if (!addressIsEqual(user.getAddress()))
			this.dao.updateAddress(user.getAddress());
	}

	private void emailIsNotUsed(String email) {
		if (getSystemUser(email).getEmail() == null)
			return;
		throw new EmailExistException("Email já em uso");
	}

	private boolean SystemUserIsValid(SystemUser user) {
		if (SystemUserValidation.systemUserIsValid(user))
			return true;
		throw new SystemUserInvalidException("Dados inválidos");
	}

	private boolean phoneIsEqual(SystemUser user) {
		return user.getPhone().equals(this.dao.getPhone(user.getId()));
	}

	private boolean addressIsEqual(Address address) {
		return address.toString().equals(this.dao.get(address.getId()).toString());
	}
}
