package br.com.pizzaria.dao;

import br.com.pizzaria.domain.SystemUser;

public interface SystemUserDao {
	
	SystemUser getSystemUser(SystemUser user);
	
	SystemUser getSystemUser(String email);
	
	SystemUser save(SystemUser user);
}
