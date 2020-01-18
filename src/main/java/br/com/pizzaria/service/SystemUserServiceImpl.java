package br.com.pizzaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pizzaria.dao.SystemUserDao;
import br.com.pizzaria.domain.SystemUser;

@Service
@Transactional
public class SystemUserServiceImpl implements SystemUserService{

	@Autowired
	private SystemUserDao dao;

	public SystemUser getSystemUser(SystemUser user) {
		return dao.getSystemUser(user);
	}
	
	public SystemUser getSystemUser(String username) {
		return dao.getSystemUser(username);
	}
}
