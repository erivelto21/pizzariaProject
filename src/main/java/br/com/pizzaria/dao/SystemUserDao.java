package br.com.pizzaria.dao;

import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.SystemUser;

public interface SystemUserDao {
	
	SystemUser getSystemUser(SystemUser user);
	
	SystemUser getSystemUser(String email);
	
	SystemUser save(SystemUser user);
	
	void updateSystemUser(SystemUser user);
	
	Address get(long id);
	
	long saveAddress(Address address);
	
	void updateAddress(Address address);
	
	String getPhone(long id);
}
