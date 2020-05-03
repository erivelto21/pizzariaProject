package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.SystemUser;

public interface SystemUserDao {

	List<SystemUser> find(long id);
	
	List<SystemUser> find(SystemUser user);

	List<SystemUser> find(String email);

	SystemUser persist(SystemUser user);

	void update(SystemUser user);

	Address findAddress(long id);

	Address persistAddress(Address address);

	void updateAddress(Address address);

	String findPhoneByUserId(long id);
}
