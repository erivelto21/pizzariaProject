package br.com.pizzaria.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pizzaria.domain.SystemUser;

@Repository
public class SystemUserDaoImpl implements SystemUserDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	public SystemUser getSystemUser(SystemUser user) {
		return this.entityManager
				.createQuery("select u from SystemUser u where u.username = :username and u.password = :password", SystemUser.class)
				.setParameter("username", user.getUsername())
				.setParameter("password", user.getPassword())
				.getSingleResult();
	}
	
	public SystemUser getSystemUser(String username) {
		return this.entityManager
				.createQuery("select u from SystemUser u where u.username = :username", SystemUser.class)
				.setParameter("username", username)
				.getSingleResult();
	}
}
