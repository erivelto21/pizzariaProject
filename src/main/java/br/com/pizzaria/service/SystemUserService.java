package br.com.pizzaria.service;

import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.SystemUser;

public interface SystemUserService {
	
	SystemUser getSystemUser(long id);
	
	SystemUser getSystemUser(SystemUser user);
	
	SystemUser getSystemUser(String email);
	
	SystemUser save(SystemUser user);
	
	void addressManagement(Address address, long systemUserId);
	
	void phoneManagement(String phone, long systemUserId);
}
