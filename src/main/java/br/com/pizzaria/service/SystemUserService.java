package br.com.pizzaria.service;

import br.com.pizzaria.domain.SystemUser;

public interface SystemUserService {
	
	SystemUser getSystemUser(SystemUser user);
	
	SystemUser getSystemUser(String email);
	
	SystemUser save(SystemUser user) throws Exception;
	
	SystemUser updateAddress(SystemUser user);
}
