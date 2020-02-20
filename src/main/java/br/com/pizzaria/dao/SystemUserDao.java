package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.SystemUser;

public interface SystemUserDao {

	List<SystemUser> getSystemUser(SystemUser user);

	List<SystemUser> getSystemUser(String email);

	SystemUser save(SystemUser user);

	void updateSystemUser(SystemUser user);

	Address get(long id);

	long saveAddress(Address address);

	void updateAddress(Address address);

	String getPhone(long id);
}
