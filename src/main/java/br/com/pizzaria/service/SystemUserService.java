package br.com.pizzaria.service;

import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.SystemUser;

public interface SystemUserService {
	
	SystemUser get(long id);
	
	SystemUser get(SystemUser user);
	
	SystemUser get(String email);
	
	SystemUser save(SystemUser user);
	
	void passwordManagement(String password, long systemUserId);
	
	void addressManagement(Address address, long systemUserId);
	
	void phoneManagement(String phone, long systemUserId);
}
