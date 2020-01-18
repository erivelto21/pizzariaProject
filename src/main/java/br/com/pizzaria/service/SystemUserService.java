package br.com.pizzaria.service;

import br.com.pizzaria.domain.SystemUser;

public interface SystemUserService {
	
	SystemUser getSystemUser(SystemUser user);
	
	SystemUser getSystemUser(String username);
}
