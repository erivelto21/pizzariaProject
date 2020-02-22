package br.com.pizzaria.service;

import br.com.pizzaria.domain.SystemUser;

public interface SystemUserService {
	
	SystemUser getSystemUser(long id);
	
	SystemUser getSystemUser(SystemUser user);
	
	SystemUser getSystemUser(String email);
	
	SystemUser save(SystemUser user);
	
	void createAddress(SystemUser user);
	
	void createPhone(SystemUser user);
}
